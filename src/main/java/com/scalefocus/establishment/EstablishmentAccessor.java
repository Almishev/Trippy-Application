package com.scalefocus.establishment;

import com.scalefocus.comment.CommentMapper;
import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.scalefocus.town.TownAccessor;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class EstablishmentAccessor {

    private static final Logger log = LoggerFactory.getLogger(TownAccessor.class);

    private EstablishmentMapper estMapper;
    private HikariDataSource dataSource;
    private CommentMapper commentMapper;

    @Autowired
    public EstablishmentAccessor(EstablishmentMapper estMapper, HikariDataSource dataSource,CommentMapper commentMapper) {
        this.estMapper = estMapper;
        this.dataSource = dataSource;
        this.commentMapper=commentMapper;
    }

    public Establishment addEstablishment(Establishment est) {
        int  estId;
        final String insertSQL = "insert into establishments (category_id,name,town_id) values (?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, est.getCategoryId());
            preparedStatement.setString(2, est.getName());
            preparedStatement.setInt(3, est.getTownId());


            log.debug("Trying to persist new establishments");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                estId = rs.getInt(1);
            } else {
                log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
                throw new SQLException("No id retrieved from inserted object");
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        est.setEstId(estId);
        log.info(String.format("Establishment with id %d successfully persisted", estId));
        return est;
    }

    public List<Establishment> readAllEstablishments(int limit) {
        final String sql = "SELECT * FROM establishments order by est_id limit ? ";
        ResultSet resultSet;
        List<Establishment> establishments;
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setInt(1,limit);
            resultSet = prepareStatement.executeQuery();

            establishments = estMapper.mapResultSetToEstablishments(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return establishments;
    }

    public Establishment readEstablishmentById(int id) {
        ResultSet resultSet;
        List<Establishment> establishments;

        final String SQL = "SELECT * FROM establishments WHERE est_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            establishments = estMapper.mapResultSetToEstablishments(resultSet);
            if (establishments.size() > 1) {
                log.error(String.format("Multiple establishments with id %d found in database", id));
                throw new IDNotUniqueException(String.format("Multiple towns with the same id found. Id = %d", id));
            } else if (establishments.size() == 0) {
                log.error(String.format("Establishment with id %d not found in database", id));
                throw new NotFoundException(String.format("Establishment not found with id %d", id));
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return establishments.get(0);
    }

    public int deleteEstablishment(int id) {
        final String deleteSQL = "DELETE FROM establishments WHERE est_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            deleteStatement.setInt(1, id);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

    public int updateEstablishment(Establishment est) {

        final String UpdateSQL = "UPDATE establishments SET category_id = ? , name = ? , town_id = ?  WHERE est_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {
            updateStatement.setInt(1,est.getCategoryId());
            updateStatement.setString(2, est.getName());
            updateStatement.setInt(3,est.getTownId());
            updateStatement.setInt(4,est.getEstId());

            return updateStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }

    }

       public boolean isTheEstablishExist(int categoryId, String name, int townId){

        final String sql= "                            " +
                "select est_id  from establishments e  " +
                "where e.category_id= ? and  e.name =  " +
                " ? and  e.town_id = ?                 " ;

               ResultSet resultSet;
               int n;
               try (Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                   statement.setInt(1,categoryId);
                   statement.setString(2,name);
                   statement.setInt(3,townId);
                   resultSet = statement.executeQuery();

                   n = commentMapper.mapResultSetToInt(resultSet);
                   return n > 0;
               } catch (SQLException e) {
                   log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
                   throw new DatabaseConnectivityException(e);
               }




       }






}
