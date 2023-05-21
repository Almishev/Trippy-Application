package com.scalefocus.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.user.UserController;
import com.scalefocus.user.UserRequest;
import com.scalefocus.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.scalefocus.user.userutility.UserConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {



        private MockMvc mockMvc;

        @Mock
        private UserService userService;

        @InjectMocks
        private UserController userController;

        @Before
        public void setup() {
            mockMvc = MockMvcBuilders
                    .standaloneSetup(userController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }

        @Test
        public void handleInvalidParameter_onEndpointGetUserById_badRequest() throws Exception {
            mockMvc.perform(get("/users/invalid"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.Errors[0]").exists());
        }

        @Test
        public void handleInvalidFormatControllerException_onEndpointCreateItem_badRequest() throws Exception {
            String json = "{\"gama@abv.bg\":\"\",\"Ant\":\"\"Anetova\"}";

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.Errors[0]").exists());
        }

        @Test
        public void handleControllerValidationException_onEndpointCreateItem_badRequest() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            UserRequest erroneousUserRequest = new UserRequest("223w", "", "");
            String json = mapper.writeValueAsString(erroneousUserRequest);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.Errors[0]").exists());
        }

        @Test
        public void handleItemNotFoundException_onEndpointGetItemById_notFound() throws Exception {
            when(userService.getUserById(USER_ID)).thenThrow(new NotFoundException("Not found"));

            mockMvc.perform(get("/users/" + USER_ID))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.Errors[0]").exists());
        }

        @Test
        public void handleDatabaseConnectivityException_onEndpointGetAllUsers_serviceUnavailable() throws Exception {
            when(userService.getAllUsers()).thenThrow(DatabaseConnectivityException.class);

            mockMvc.perform(get("/users"))
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(jsonPath("$.Errors[0]").exists());
            ;
        }

        @Test
        public void handleUnexpectedException_onEndpointGetAllUsers_internalServerError() throws Exception {
            when(userService.getAllUsers()).thenThrow(IllegalStateException.class);

            mockMvc.perform(get("/users"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.Errors[0]").exists());
        }


    }