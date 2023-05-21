package com.scalefocus.town;
import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.user.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
@Component
public class TownAccessor {

    private static final Logger log = LoggerFactory.getLogger(TownAccessor.class);

    private TownMapper townMapper;
    private HikariDataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Autowired
    public TownAccessor(TownMapper townMapper, HikariDataSource dataSource) {
        this.townMapper = townMapper;
        this.dataSource = dataSource;
    }

    public Town addTown(Town town) {
        int  townId;
        final String insertSQL = "insert into towns(name) values (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, town.getName());


            log.debug("Trying to persist new town");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
               townId = rs.getInt(1);
            } else {
                log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
                throw new SQLException("No id retrieved from inserted object");
            }

        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        town.setTownId(townId);
        log.info(String.format("User with id %d successfully persisted", townId));
        return town;
    }

    public List<Town> readAllTowns() {
        ResultSet resultSet;
        List<Town> towns;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM towns order by town_id");

            towns = townMapper.mapResultSetToTowns(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return towns;
    }

    public Town readTownById(int id) {
        ResultSet resultSet;
        List<Town> towns;

        final String SQL = "SELECT * FROM towns WHERE town_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            towns = townMapper.mapResultSetToTowns(resultSet);
            if (towns.size() > 1) {
                log.error(String.format("Multiple towns with id %d found in database", id));
                throw new IDNotUniqueException(String.format("Multiple towns with the same id found. Id = %d", id));
            } else if (towns.size() == 0) {
                log.error(String.format("Town with id %d not found in database", id));
                throw new NotFoundException(String.format("Town not found with id %d", id));
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return towns.get(0);
    }


    public int deleteTown(int id) {
        final String deleteSQL = "DELETE FROM towns WHERE town_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            deleteStatement.setInt(1, id);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

    public int updateTown(Town town) {

        final String UpdateSQL = "UPDATE towns SET name = ?  WHERE town_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {
            updateStatement.setString(1, town.getName());
            updateStatement.setInt(2,town.getTownId());

            return updateStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

    public boolean isTownExist(String town){

        final String sql= "select name from towns where name = ?";

        ResultSet resultSet;
        String name = "";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,town);
            resultSet = statement.executeQuery();

            name= userMapper.mapResultSetToString(resultSet);
            return name.length() > 0;
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }




    }

}
