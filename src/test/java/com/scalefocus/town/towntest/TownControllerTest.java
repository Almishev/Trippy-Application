package com.scalefocus.town.towntest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.town.Town;
import com.scalefocus.town.TownController;
import com.scalefocus.town.TownService;
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

import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class TownControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TownService service;

    @InjectMocks
    private TownController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllTowns_singleItem_success() throws Exception {
        when(service.getAllTowns()).thenReturn(Collections.<Town>singletonList(getDefaultTown()));

        mockMvc.perform(get("/towns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].townId").value(TOWN_ID))
                .andExpect(jsonPath("$[0].name").value(TOWN_NAME))
                .andExpect((jsonPath("$",hasSize(1))));
    }

    @Test
    public void getAllItems_emptyList_success() throws Exception {
        when(service.getAllTowns()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/towns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect((jsonPath("$",hasSize(0))));
    }
    @Test
    public void createTownSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultTownRequest());

        when(service.addTown(any())).thenReturn(getDefaultTown());
        mockMvc.perform(post("/towns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/towns/1"));
    }

    @Test
    public void getTownByIdSuccess() throws Exception {
        when(service.getTownById(TOWN_ID)).thenReturn(getDefaultTown());

        mockMvc.perform(get("/towns/" + TOWN_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").value(TOWN_NAME));
    }


    @Test
    public void deleteTownSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultTownRequest());

        when(service.removeTown(TOWN_ID)).thenReturn(getDefaultTownDto());

        mockMvc.perform(delete("/towns/" + TOWN_ID)
                        .content(json))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteTownRequestedResponseSuccess() throws Exception {

        when(service.removeTown(TOWN_ID)).thenReturn(getDefaultTownDto());

        mockMvc.perform(delete("/towns/" + TOWN_ID)
                        .queryParam("returnOld", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").value(TOWN_NAME));
    }

    @Test
    public void updateTownNoResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultTownRequest());

        when(service.editTown(any(), eq(TOWN_ID))).thenReturn(getDefaultTownDto());

        mockMvc.perform(put("/towns/" + TOWN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateTownRequestedResponseSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultTownRequest());

        when(service.editTown(any(), eq(TOWN_ID))).thenReturn(getDefaultTownDto());

        mockMvc.perform(put("/towns/" + TOWN_ID)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").value(TOWN_NAME));
    }



}

