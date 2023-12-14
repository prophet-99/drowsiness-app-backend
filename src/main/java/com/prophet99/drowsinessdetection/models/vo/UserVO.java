package com.prophet99.drowsinessdetection.models.vo;

import com.prophet99.drowsinessdetection.models.documents.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

public class UserVO {
  private String dni; // DNI
  private String name;
  private String lastName;
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
    return "UserVO{" +
      "dni='" + dni + '\'' +
      ", name='" + name + '\'' +
      ", lastName='" + lastName + '\'' +
      ", fullName='" + fullName + '\'' +
      ", email='" + email + '\'' +
      ", cellphone='" + cellphone + '\'' +
      ", active=" + active +
      '}';
  }

  public User getUserEntity() {
    User user = new User();
    user.setDni(getDni());
    user.setName(getName());
    user.setLastName(getLastName());
    user.setFullName(getFullName());
    user.setEmail(getEmail());
    user.setCellphone(getCellphone());
    user.setActive(getActive());

    return user;
  }
}
