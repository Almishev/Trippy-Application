package com.scalefocus.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.scalefocus.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  private static final Logger log = LoggerFactory.getLogger(UserAccessor.class);

  public List<User> mapResultSetToUsers(ResultSet usersResultSet) {
    List<User> usersList = new ArrayList<>();
    try (usersResultSet) {
      while (usersResultSet.next()) {
        int id = usersResultSet.getInt(1);
        String email = usersResultSet.getString(2);
        String fName = usersResultSet.getString(3);
        String lName = usersResultSet.getString(4);
        Timestamp createOn=usersResultSet.getTimestamp(5);
        Timestamp editOn=usersResultSet.getTimestamp(6);
        User user = new User(id, email, fName, lName,createOn,editOn);
        usersList.add(user);
      }
    } catch (SQLException e) {
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
    return usersList;
  }



  public String mapResultSetToString(ResultSet commentResultSet){
    String status = "";
    try (commentResultSet){
      while (commentResultSet.next()) {
        status = commentResultSet.getString(1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return status;
  }


}
