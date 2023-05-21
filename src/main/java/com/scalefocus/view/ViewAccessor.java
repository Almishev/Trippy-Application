package com.scalefocus.view;

import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.town.TownAccessor;
import com.scalefocus.view.view.model.*;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class ViewAccessor {

    private static final Logger log = LoggerFactory.getLogger(TownAccessor.class);

    private ViewMapper viewMapper;
    private HikariDataSource dataSource;

    @Autowired
    public ViewAccessor(ViewMapper viewMapper, HikariDataSource dataSource) {
        this.viewMapper = viewMapper;
        this.dataSource = dataSource;
    }

    public List<ViewTown> readAllViewsFromTownName(String town) {
        ResultSet resultSet;
        List<ViewTown> views;
        final String sqlQuery = "" +
                "select c.name as type, e.name  as company ,          " +
                "round(avg(com.rating),2) as total_rating,            " +
                "count(com.content) as total_comment                  " +
                "from categories  c inner join establishments e       " +
                "on c.category_id=e.category_id                       " +
                "inner join towns t on e.town_id=t.town_id            " +
                "left outer join comments com on e.est_id= com.est_id " +
                "left outer join users u                              " +
                "on com.user_id = u.user_id                           " +
                "where t.name = ?                                     " +
                "group by c.name,e.name                               " +
                "order by total_rating DESC NULLS LAST                ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setString(1, town);
            resultSet = prepareStatement.executeQuery();

            views = viewMapper.mapResultSetToViews(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return views;
    }


    public List<ViewBasic> readAllViews(int limit) {
        ResultSet resultSet;
        List<ViewBasic> views;
        final String sqlQuery = "select t.name as town, c.name as type, e.name   " +
                "                 as company ,                                        " +
                "                round(avg(com.rating),2) as total_rating             " +
                "                from categories  c inner join establishments e       " +
                "                on c.category_id=e.category_id                       " +
                "                inner join towns t on e.town_id=t.town_id            " +
                "                left outer join comments com on e.est_id= com.est_id " +
                "                left outer join users u                              " +
                "                on com.user_id = u.user_id                           " +
                "                group by c.name,t.name,e.name                        " +
                "                order by total_rating DESC NULLS LAST                " +
                "                limit ?                                                ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            views = viewMapper.mapResultSetToViewBasics(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return views;
    }


    public List<ViewRate> showBusinessFromTownAndByRate(String town, double rate) {
        ResultSet resultSet;
        List<ViewRate> views;
        final String SQL = "" +
                "select c.name as type,e.name  as company ,    " +
                "com.rating as rate                            " +
                "from categories  c inner join establishments e" +
                " on c.category_id=e.category_id               " +
                "inner join towns t on e.town_id=t.town_id     " +
                " left outer join comments com on              " +
                "e.est_id= com.est_id left outer join users u  " +
                "on com.user_id = u.user_id where t.name = ?   " +
                " AND com.rating > ?                           ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
             preparedStatement.setString(1,town);
             preparedStatement.setDouble(2, rate);
             resultSet = preparedStatement.executeQuery();

            views = viewMapper.mapResultSetToViewRates(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return views;

    }


    public List<ViewComments> readComments(String name) {
        ResultSet resultSet;
        List<ViewComments> viewComments;
        final String sqlQuery = "" +
                "select e.name  as company ,                           " +
                "u.email as user, com.content,                         " +
                "com.rating as rating,com.created_on date              " +
                "from categories  c inner join establishments e        " +
                "on c.category_id=e.category_id                        " +
                "inner join towns t on e.town_id=t.town_id             " +
                "left outer join comments com on e.est_id= com.est_id  " +
                "left outer join users u                               " +
                "on com.user_id = u.user_id where e.name = ?           ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setString(1, name);
            resultSet = prepareStatement.executeQuery();

            viewComments = viewMapper.mapResultSetToViewComments(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return viewComments;


    }

    public List<ViewType> readAllViewsForType(String type,int lowest, int highest) {
        ResultSet resultSet;
        List<ViewType> views;
        final String sqlQuery = "" +
                "select t.name as town, e.name  as company ,          " +
                "round(avg(com.rating),2) as total_rating             " +
                "from categories  c inner join establishments e       " +
                "on c.category_id=e.category_id                       " +
                "inner join towns t on e.town_id=t.town_id            " +
                "left outer join comments com on e.est_id= com.est_id " +
                "left outer join users u                              " +
                "on com.user_id = u.user_id                           " +
                "where c.name = ?  and  e.est_id > ?                  " +
                " and e.est_id < ?                                    " +
                "group by t.name,e.name                               " +
                "order by total_rating DESC NULLS LAST                ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setString(1, type);
            prepareStatement.setInt(2,lowest);
            prepareStatement.setInt(3,highest);
            resultSet = prepareStatement.executeQuery();

            views = viewMapper.mapResultSetToViewType(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return views;
    }

    public List<ViewCount> readAllCounts() {
        ResultSet resultSet;
        List<ViewCount> views;
        final String sqlQuery = "select                                  " +
                "(select count(category_id) from categories) as type,    " +
                "(select count(town_id) from towns) as towns,            " +
                "(select count(est_id) as companies from establishments)," +
                "(select count (user_id) as users  from users),          " +
                "(select count (comment_id) as comments from comments)   ";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sqlQuery);

              views = viewMapper.mapResultSetToViewCounts(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return views;
    }
}
