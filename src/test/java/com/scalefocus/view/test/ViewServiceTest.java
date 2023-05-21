package com.scalefocus.view.test;

import com.scalefocus.user.User;
import com.scalefocus.view.ViewAccessor;
import com.scalefocus.view.ViewService;
import com.scalefocus.view.view.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.user.userutility.UserFactory.getDefaultUser;
import static com.scalefocus.view.utility.ViewFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewServiceTest {

    @Mock
    private ViewAccessor accessor;

    @InjectMocks
    private ViewService service;


    @Test
    public void getAllViewTypesSuccess(){
        List<ViewType> viewTypes = Collections.singletonList(getDefaultViewType());

        when(accessor.readAllViewsForType("hotel",1,10)).thenReturn(viewTypes);

        List<ViewType> result = service.readAllViewTypes("hotel",1,10);
        assertEquals(result,viewTypes);
    }

    @Test
    public void getAllViewTownsSuccess(){
        List<ViewTown> viewTown = Collections.singletonList(getDefaultViewTown());

        when(accessor.readAllViewsFromTownName("Rome")).thenReturn(viewTown);

        List<ViewTown> result = service.readAllViewsFromTownName("Rome");
        assertEquals(result,viewTown);
    }

    @Test
    public void getAllViewRatesSuccess(){
        List<ViewRate> viewRate = Collections.singletonList(getDefaultViewRate());

        when(accessor.showBusinessFromTownAndByRate("Rome",1)).thenReturn(viewRate);

        List<ViewRate> result = service.getAllViewsFromTownAndRate("Rome",1);
        assertEquals(result,viewRate);
    }

    @Test
    public void getAllViewCountsSuccess(){
        List<ViewCount> viewCount = Collections.singletonList(getDefaultViewCount());

        when(accessor.readAllCounts()).thenReturn(viewCount);

        List<ViewCount> result = service.getAllCounts();
        assertEquals(result,viewCount);
    }

    @Test
    public void getAllViewBasicsSuccess(){
        List<ViewBasic> viewBasic = Collections.singletonList(getDefaultViewBasic());

        when(accessor.readAllViews(10)).thenReturn(viewBasic);

        List<ViewBasic> result = service.getAllViews(10);
        assertEquals(result,viewBasic);
    }

    @Test
    public void getAllViewCommentsSuccess(){
        List<ViewComments> viewComments = Collections.singletonList(getDefaultViewComment());

        when(accessor.readComments("Tiber")).thenReturn(viewComments);

        List<ViewComments> result = service.readAllViewCommentsByEstablishmentName("Tiber");
        assertEquals(result,result);
    }








}