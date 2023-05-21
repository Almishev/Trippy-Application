package com.scalefocus.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserRequest {

  @Email(message = "Email must be only in valid pattern")
  private String email;

  @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
  private String fName;

  @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
  private String lName;

  public UserRequest() {
  }

  public UserRequest(String email, String fName, String lName) {
    this.email = email;
    this.fName = fName;
    this.lName = lName;
  }

  public String getEmail() {
    return email;
  }

  public String getfName() {
    return fName;
  }

  public String getlName() {
    return lName;
  }
}
