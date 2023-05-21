package com.scalefocus.view.test;

import com.scalefocus.view.ViewController;
import com.scalefocus.view.ViewService;
import com.scalefocus.view.view.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.scalefocus.view.utility.ViewConstants.*;
import static com.scalefocus.view.utility.ViewFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ViewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ViewService service;

    @InjectMocks
    private ViewController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllViewRateSingleViewRateSuccess() throws Exception {
        when(service.getAllViewsFromTownAndRate(VIEW_RATE_TYPE,1)).thenReturn(Collections.<ViewRate>singletonList(getDefaultViewRate()));

        mockMvc.perform(get("/rate").queryParam("town","Bansko").queryParam("rate","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllViewRateEmptyListSuccess() throws Exception {
        when(service.getAllViewsFromTownAndRate("Kartagen",1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rate").queryParam("town","Kartagen").queryParam("rate","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void getAllViewCountSingleViewCountSuccess() throws Exception {
        when(service.getAllCounts()).thenReturn(Collections.<ViewCount>singletonList(getDefaultViewCount()));

        mockMvc.perform(get("/pieces"))
                .andExpect(status().isOk());
    }

    @Test
    public void getViewBasicSingleViewBasicSuccess() throws Exception {
        when(service.getAllViews(1)).thenReturn(Collections.<ViewBasic>singletonList(getDefaultViewBasic()));

        mockMvc.perform(get("/views"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllViewTownsSingleViewTownSuccess() throws Exception {
        when(service.readAllViewsFromTownName("Bansko")).thenReturn(Collections.singletonList(getDefaultViewTown()));

        mockMvc.perform(get("/view/v1").queryParam("town","Bansko"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(VIEW_TOWN_TYPE))
                .andExpect(jsonPath("$[0].company").value(VIEW_TOWN_COMPANY))
                .andExpect(jsonPath("$[0].totalRating").value(VIEW_TOWN_AVG_RATING))
                .andExpect(jsonPath("$[0].totalComments").value(VIEW_TOWN_TOTAL_COMMENTS));
    }

    @Test
    public void getAllViewTownsSingleViewTownEmptyListSuccess() throws Exception {
        when(service.readAllViewsFromTownName("Bansko")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/view/v1").queryParam("town","Bansko"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void getAllViewsByTypeSingleViewTypeSuccess() throws Exception {
        when(service.readAllViewTypes(VIEW_TYPE_NAME,1,10)).thenReturn(Collections.<ViewType>singletonList(getDefaultViewType()));

        mockMvc.perform(get("/view/v2").queryParam("type","hotel"))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllViewContentSingleViewContentSuccess() throws Exception {
        when(service.readAllViewCommentsByEstablishmentName(VIEW_COMMENT_COMPANY)).thenReturn(Collections.<ViewComments>singletonList(getDefaultViewComment()));

        mockMvc.perform(get("/content").queryParam("name","Tiber"))
                .andExpect(status().isOk());

    }


}