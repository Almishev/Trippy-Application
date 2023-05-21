package com.scalefocus.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class UserService {

  private final UserAccessor userAccessor;

  @Autowired
  public UserService(UserAccessor userAccessor, UserMapper userMapper) {
    this.userAccessor = userAccessor;
  }

  public List<User> getAllUsers() {
    List<User> users;
         users=   userAccessor.readAllUsers();

    return users;
  }

  public User getUserById(int id) {
    return userAccessor.readUserById(id);
  }



  public User addUser(UserRequest itemRequest) {
    if(!userAccessor.isEmailExist(itemRequest.getEmail())){
    User user = new User(itemRequest.getEmail(), itemRequest.getfName(), itemRequest.getlName());
    user = userAccessor.addUser(user);
    return user;
  } else {
      throw new NullPointerException("User with this email already exist!");
    }
  }


  public int updateUser(int id, String email, String fName, String lName) {

    User user = getUserById(id);
    user.setId(id);
    user.setEmail(email);
    user.setfName(fName);
    user.setLName(lName);
    user.setCreatedOn(user.getCreatedOn());
    user.setEditedOm(Timestamp.valueOf(LocalDateTime.now()));
    return userAccessor.updateUser(user);
  }



  public UserDto removeUser(int id) {
    User oldUser = getUserById(id);
    userAccessor.deleteUser(id);
    return new UserDto(oldUser.getEmail(), oldUser.getfName(), oldUser.getLName());
  }


}
