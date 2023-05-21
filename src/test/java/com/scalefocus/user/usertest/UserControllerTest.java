package com.scalefocus.user.usertest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.user.User;
import com.scalefocus.user.UserController;
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

import java.util.Collections;

import static com.scalefocus.user.userutility.UserConstants.*;
import static com.scalefocus.user.userutility.UserFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllUsers_singleUser_success() throws Exception {
        when(service.getAllUsers()).thenReturn(Collections.<User>singletonList(getDefaultUser()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(USER_ID))
                .andExpect(jsonPath("$[0].email").value(USER_EMAIL))
                .andExpect(jsonPath("$[0].fName").value(FIRST_NAME));
              //  .andExpect(jsonPath("$[0].lName").value(LAST_NAME))
              //  .andExpect(jsonPath("$[0].createdOn").value(CREATED_ON))
          //      .andExpect(jsonPath("$[0].editedOn").value(EDITED_ON));
    }

    @Test
    public void getAllUsers_emptyList_success() throws Exception {
        when(service.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void getUserByIdNoExceptionsSuccess() throws Exception {
        when(service.getUserById(USER_ID)).thenReturn(getDefaultUser());

        mockMvc.perform(get("/users/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fName").value(FIRST_NAME));
              //  .andExpect(jsonPath("$.lName").value(LAST_NAME));
    }


    @Test
    public void createUserNoExceptionsSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        when(service.addUser(any())).thenReturn(getDefaultUser());
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"));
    }

    @Test
    public void updateUserSuccess() throws Exception {
       when(service.updateUser(USER_ID,USER_EMAIL,FIRST_NAME,LAST_NAME)).thenReturn(1);
        mockMvc.perform(put("/users/" + USER_ID)
                        .queryParam("email", "toni@abv.bg").queryParam("fName","Onoma").queryParam("lName","Famili"))
                .andExpect(status().isOk());
    }



    @Test
    public void deleteUserNoResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        when(service.removeUser(USER_ID)).thenReturn(getDefaultUserDto());

        mockMvc.perform(delete("/users/" + USER_ID))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteUserRequestedResponseSuccess() throws Exception {

        when(service.removeUser(USER_ID)).thenReturn(getDefaultUserDto());

        mockMvc.perform(delete("/users/" + USER_ID)
                        .queryParam("returnOld", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.email").value(USER_EMAIL));
    }


}