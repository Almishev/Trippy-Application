package com.scalefocus.town;

import com.scalefocus.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class TownMapper {
    private static final Logger log = LoggerFactory.getLogger(TownAccessor.class);
    public List<Town> mapResultSetToTowns(ResultSet townsResultSet) {
        List<Town> townsList = new ArrayList<>();
        try (townsResultSet) {
            while (townsResultSet.next()) {
                int id = townsResultSet.getInt(1);
                String name = townsResultSet.getString(2);

                Town town = new Town(id, name);
                townsList.add(town);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return townsList;
    }

}
