package com.scalefocus.user;

import java.sql.Timestamp;

public class UserDto {

  private String email;
  private String fName;
  private String lName;

  private Timestamp editedOn;

  public UserDto() {
  }

  public UserDto(String email, String fName, String lName) {
    this.email = email;
    this.fName = fName;
    this.lName = lName;
  }


  public Timestamp getEditedOn() {
    return editedOn;
  }

  public String getEmail() {
    return email;
  }

  public String getFName() {
    return fName;
  }

  public String getLName() {
    return lName;
  }
}
