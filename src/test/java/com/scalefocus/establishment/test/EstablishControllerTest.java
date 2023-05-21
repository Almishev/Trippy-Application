package com.scalefocus.establishment.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.establishment.EstablishController;
import com.scalefocus.establishment.EstablishService;
import com.scalefocus.establishment.Establishment;
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

import static com.scalefocus.establishment.utility.EstConstants.*;
import static com.scalefocus.establishment.utility.EstFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class EstablishControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EstablishService service;

    @InjectMocks
    private EstablishController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllItemsSingleItemSuccess() throws Exception {
        when(service.getAllEstablishments(1)).thenReturn(Collections.<Establishment>singletonList(getDefaultEst()));

        mockMvc.perform(get("/ests"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getAllEstsEmptyListSuccess() throws Exception {
        when(service.getAllEstablishments(3)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/ests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void getEstByIdNoExceptionsSuccess() throws Exception {
        when(service.getEstablishmentById(EST_ID)).thenReturn(getDefaultEst());

        mockMvc.perform(get("/ests/" +EST_ID))
                .andExpect(status().isOk());
    }


    @Test
    public void createEstNoExceptionsSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultEstRequest());

        when(service.addEstablish(any())).thenReturn(getDefaultEst());
        mockMvc.perform(post("/ests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/ests/1"));
    }

    @Test
    public void updateEstNoResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultEstRequest());

        when(service.editEst(any(), eq(EST_ID))).thenReturn(getDefaultEstDto());

        mockMvc.perform(put("/ests/" + EST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateEstRequestedResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultEstRequest());

        when(service.editEst(any(), eq(EST_ID))).thenReturn(getDefaultEstDto());

        mockMvc.perform(put("/ests/" + EST_ID)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEstNoResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultEstRequest());

        when(service.removeEst(EST_ID)).thenReturn(getDefaultEstDto());

        mockMvc.perform(delete("/ests/" + EST_ID) .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteEstRequestedResponseSuccess() throws Exception {

        when(service.removeEst(EST_ID)).thenReturn(getDefaultEstDto());

        mockMvc.perform(delete("/ests/" + EST_ID)
                        .queryParam("returnOld", "true"))
                .andExpect(status().isOk());
    }

}