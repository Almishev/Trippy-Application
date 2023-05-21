package com.scalefocus.town.towntest;

import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.town.Town;
import com.scalefocus.town.TownAccessor;
import com.scalefocus.town.TownMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTown;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TownAccessorTest {

    @Mock
    TownMapper mapper;
    @Mock
    private HikariDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TownAccessor accessor;

    @Before
    public void setupConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @After
    public void verifyConnectionClosed() throws SQLException {
        verify(connection, times(1)).close();
    }

    @Test
    public void addTown_noExceptions_success() throws SQLException {

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(eq(1))).thenReturn(TOWN_ID);
        // act
        Town result = accessor.addTown(new Town(TOWN_NAME));
        // assert
        verify(preparedStatement, times(1)).executeUpdate();
        verify(resultSet, times(1)).getInt(1);

        verify(preparedStatement, times(1)).close();

        assertEquals(TOWN_ID, result.getTownId());
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void addTown_statementPreparationSQLException_databaseConnectivityException() throws SQLException {
        // arrange
        when(connection.prepareStatement(any(), anyInt())).thenThrow(SQLException.class);

        accessor.addTown(new Town("Town"));
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void addTown_emptyGeneratedKeysResultSet_databaseConnectivityException() throws SQLException {

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        accessor.addTown(new Town("TestTown"));

        verify(preparedStatement, times(1)).close();
    }

    @Test
    public void readAllTowns_noExceptions_success() throws SQLException {
        Statement statement = Mockito.mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(mapper.mapResultSetToTowns(resultSet)).thenReturn(Collections.singletonList(getDefaultTown()));

        List<Town> result = accessor.readAllTowns();


        verify(statement, times(1)).close();
        assertEquals(TOWN_ID, result.get(0).getTownId());
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void readAllItems_statementSQLException_DatabaseConnectivityException() throws SQLException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        accessor.readAllTowns();
    }

    @Test
    public void readItemById_noExceptions_success() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.mapResultSetToTowns(resultSet)).thenReturn(Collections.singletonList(getDefaultTown()));

        Town result = accessor.readTownById(TOWN_ID);

        verify(preparedStatement, times(1)).close();
        assertEquals(TOWN_ID, result.getTownId());
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void readItemById_statementPreparationSQLException_DatabaseConnectivityException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        accessor.readTownById(TOWN_ID);
    }

    @Test(expected = IDNotUniqueException.class)
    public void readItemById_multipleItemsWithSameId_IDNotUniqueException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.mapResultSetToTowns(resultSet)).thenReturn(Arrays.asList(getDefaultTown(), getDefaultTown()));

        accessor.readTownById(TOWN_ID);

        verify(preparedStatement, times(1)).close();
    }

    @Test(expected = NotFoundException.class)
    public void readItemById_noItemsFoundWithId_ItemNotFoundException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.mapResultSetToTowns(resultSet)).thenReturn(Collections.emptyList());

        accessor.readTownById(TOWN_ID);

        verify(preparedStatement, times(1)).close();
    }

    @Test
    public void updateItem_noExceptions_success() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        accessor.updateTown(getDefaultTown());

        verify(preparedStatement, times(1)).close();
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void updateItem_statementPreparationSQLException_DatabaseConnectivityException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        accessor.updateTown(getDefaultTown());
    }

    @Test
    public void deleteItem_noExceptions_success() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        accessor.deleteTown(TOWN_ID);

        verify(preparedStatement, times(1)).close();
    }

    @Test(expected = DatabaseConnectivityException.class)
    public void deleteTown_statementPreparationSQLException_DatabaseConnectivityException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        accessor.deleteTown(TOWN_ID);
    }

}