package com.scalefocus.town.towntest;

import com.scalefocus.town.Town;
import com.scalefocus.town.TownDto;
import com.scalefocus.town.TownMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTown;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TownMapperTest {

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TownMapper mapper;


    @Test
    public void mapResultSetToTowns_noExceptions_success() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(anyInt())).thenReturn(TOWN_ID);
        when(resultSet.getString(anyInt())).thenReturn(TOWN_NAME);

        List<Town> townList = mapper.mapResultSetToTowns(resultSet);

        assertEquals(1, townList.size());
        assertEquals(TOWN_NAME, townList.get(0).getName());
    }

    @Test(expected = RuntimeException.class)
    public void mapResultSetToTowns_resultSetSQLException_success() throws SQLException {
        when(resultSet.next()).thenThrow(new SQLException());

        mapper.mapResultSetToTowns(resultSet);
    }

}