package com.scalefocus.town.towntest;

import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.town.*;
import com.scalefocus.user.User;
import com.scalefocus.user.UserDto;
import com.scalefocus.user.UserMapper;
import com.scalefocus.user.UserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.*;
import static com.scalefocus.user.userutility.UserConstants.USER_EMAIL;
import static com.scalefocus.user.userutility.UserFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class TownServiceTest {

    @Mock
    private TownAccessor accessor;
    @Mock
    private TownMapper mapper;

    @InjectMocks
    private TownService service;

    @Test
    public void  saveTownTest(){
        Town town = getDefaultTown();
        TownRequest townRequest = getDefaultTownRequest();
        when(accessor.isTownExist(TOWN_NAME)).thenReturn(false);
        assertEquals(accessor.addTown(town),service.addTown(townRequest));
    }



    @Test
    public void getTownByIdTest(){
        Town town = getDefaultTown();
        when(accessor.readTownById(town.getTownId())).thenReturn(town);
        assertEquals(service.getTownById(1),town);

    }


    @Test
    public void editTownNoExceptionsSuccess() {
        when(service.getTownById(TOWN_ID)).thenReturn(getDefaultTown());
        TownRequest townRequest = new TownRequest("New Name");
        TownDto result = service.editTown(townRequest, TOWN_ID);

        verify(accessor, times(1)).readTownById(TOWN_ID);
        assertEquals(TOWN_NAME, result.getName());
    }

    @Test
    public void deleteTownNoExceptions_success() {
        when(service.getTownById(TOWN_ID)).thenReturn(getDefaultTown());

        service.removeTown(TOWN_ID);

        verify(accessor, times(1)).readTownById(TOWN_ID);
        verify(accessor, times(1)).deleteTown(TOWN_ID);
    }

    @Test
    public void getAllTownsSuccess(){
        List<Town> towns = Collections.singletonList(getDefaultTown());
        when(accessor.readAllTowns()).thenReturn(towns);
        List<Town> result = service.getAllTowns();
        assertEquals(result,towns);
    }



}