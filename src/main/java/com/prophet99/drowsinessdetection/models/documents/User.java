package com.prophet99.drowsinessdetection.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {
  @Id
  @Field(value = "id")
  private String dni; // DNI
  private String name;
  private String lastName;
  @Field(value = "_fullName")
  private String fullName;
  private String email;
  private String cellphone;
  private Boolean active;

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "User{" +
      "dni='" + dni + '\'' +
      ", name='" + name + '\'' +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      ", cellphone='" + cellphone + '\'' +
      ", active=" + active +
      '}';
  }
}
