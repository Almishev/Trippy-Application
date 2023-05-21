package com.scalefocus.user;

import java.sql.Timestamp;

public class User {

  private int id;
  private String email;
  private String fName;
  private String lName;
  private Timestamp createdOn;
  private Timestamp editedOn;

  public User() {
  }

  public User(String email, String fName, String lName) {
    this.email = email;
    this.fName = fName;
    this.lName = lName;
  }

  public User(int id, String email, String fName, String lName, Timestamp createdOn, Timestamp editedOn) {
    this.id = id;
    this.email = email;
    this.fName = fName;
    this.lName = lName;
    this.createdOn = createdOn;
    this.editedOn = editedOn;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getLName() {
    return lName;
  }

  public void setLName(String LName) {
    this.lName = LName;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  public Timestamp getEditedOm() {
    return editedOn;
  }

  public void setEditedOm(Timestamp editedOm) {
    this.editedOn = editedOm;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", fName='" + fName + '\'' +
            ", lName='" + lName + '\'' +
            ", createdOn=" + createdOn +
            ", editedOm=" + editedOn +
            '}';
  }
}
