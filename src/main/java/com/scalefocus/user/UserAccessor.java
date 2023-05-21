package com.scalefocus.user;

import com.scalefocus.exception.DatabaseConnectivityException;
import com.scalefocus.exception.IDNotUniqueException;
import com.scalefocus.exception.NotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

import static java.time.LocalTime.now;

@Component
public class UserAccessor {

    private static final Logger log = LoggerFactory.getLogger(UserAccessor.class);

    private UserMapper userMapper;
    private HikariDataSource dataSource;

    @Autowired
    public UserAccessor(UserMapper userMapper, HikariDataSource dataSource) {
        this.userMapper = userMapper;
        this.dataSource = dataSource;
    }


    public User addUser(User user) {
        int userId;
        final String insertSQL = "insert into users(email,first_name,last_name) values (?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getfName());
            preparedStatement.setString(3, user.getLName());

            log.debug("Trying to persist new user");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
                throw new SQLException("No id retrieved from inserted object");
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        user.setId(userId);
        log.info(String.format("User with id %d successfully persisted", userId));
        return user;
    }

    public List<User> readAllUsers() {
        ResultSet resultSet;
        List<User> users;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM users order by user_id");

            users = userMapper.mapResultSetToUsers(resultSet);
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return users;
    }


    public User readUserById(int id) {
        ResultSet resultSet;
        List<User> users;

        final String SQL = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            users = userMapper.mapResultSetToUsers(resultSet);
            if (users.size() > 1) {
                log.error(String.format("Multiple users with id %d found in database", id));
                throw new IDNotUniqueException(String.format("Multiple users with the same id found. Id = %d", id));
            } else if (users.size() == 0) {
                log.error(String.format("User with id %d not found in database", id));
                throw new NotFoundException(String.format("User not found with id %d", id));
            }
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
        return users.get(0);
    }


    public int updateUser(User user) {

        final String UpdateSQL = "UPDATE users SET email = ?, first_name = ?, last_name = ?  WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {
            updateStatement.setString(1, user.getEmail());
            updateStatement.setString(2, user.getfName());
            updateStatement.setString(3, user.getLName());
            updateStatement.setInt(4, user.getId());


            return updateStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

    public int deleteUser(int id) {
        final String deleteSQL = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            deleteStatement.setInt(1, id);
            return deleteStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

    public boolean isEmailExist(String email) {

        final String sql = "select email from users where email = ? ";

        ResultSet resultSet;
        String str;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            resultSet = statement.executeQuery();

            str = userMapper.mapResultSetToString(resultSet);
            return str.length() > 0;
        } catch (SQLException e) {
            log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
            throw new DatabaseConnectivityException(e);
        }
    }

}
