package com.scalefocus.establishment;

import com.scalefocus.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EstablishmentMapper {

    private static final Logger log = LoggerFactory.getLogger(EstablishmentAccessor.class);

    public List<Establishment> mapResultSetToEstablishments(ResultSet establishmentsResultSet) {
        List<Establishment> establishmentsList = new ArrayList<>();
        try (establishmentsResultSet) {
            while (establishmentsResultSet.next()) {
                int estId = establishmentsResultSet.getInt(1);
                int categoryId = establishmentsResultSet.getInt(2);
                String name = establishmentsResultSet.getString(3);
                int townId = establishmentsResultSet.getInt(4);

                Establishment establishment = new Establishment(estId,categoryId,name,townId);
                establishmentsList.add(establishment);
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return establishmentsList;
    }


}
