package com.scalefocus.establishment.test;

import com.scalefocus.establishment.*;
import com.scalefocus.town.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.establishment.utility.EstConstants.EST_ID;
import static com.scalefocus.establishment.utility.EstConstants.NAME;
import static com.scalefocus.establishment.utility.EstFactory.getDefaultEst;
import static com.scalefocus.establishment.utility.EstFactory.getDefaultEstRequest;
import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTown;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTownRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EstablishServiceTest {

    @Mock
    private EstablishmentAccessor accessor;
    @Mock
    private EstablishmentMapper mapper;

    @InjectMocks
    private EstablishService service;

    @Test
    public void getAllEstsSuccess(){
        List<Establishment> ests = Collections.singletonList(getDefaultEst());
        when(accessor.readAllEstablishments(3)).thenReturn(ests);
        List<Establishment> result = service.getAllEstablishments(3);
        assertEquals(result,ests);
    }

    @Test
    public void deleteEstNoExceptionsSuccess() {
        when(service.getEstablishmentById(EST_ID)).thenReturn(getDefaultEst());

        service.removeEst(EST_ID);

        verify(accessor, times(1)).readEstablishmentById(EST_ID);
        verify(accessor, times(1)).deleteEstablishment(EST_ID);
    }

    @Test
    public void getEstByIdTest(){
        Establishment est = getDefaultEst();
        when(accessor.readEstablishmentById(est.getEstId())).thenReturn(est);
        assertEquals(service.getEstablishmentById(1),est);

    }



    @Test
    public void editEstNoExceptionsSuccess() {
        when(service.getEstablishmentById(EST_ID)).thenReturn(getDefaultEst());
        EstRequest estRequest = new EstRequest(1,"New Name",1);

        EstDto result = service.editEst(estRequest,EST_ID);

        verify(accessor, times(1)).readEstablishmentById(EST_ID);
        assertEquals(NAME, result.getName());
    }

    @Test
    public void  saveEstablishmentTest(){
        Establishment est = getDefaultEst();
        EstRequest estRequest = getDefaultEstRequest();
        when(accessor.isTheEstablishExist(est.getCategoryId(),est.getName(),est.getTownId())).thenReturn(false);
        assertEquals(accessor.addEstablishment(est),service.addEstablish(estRequest));
    }



}