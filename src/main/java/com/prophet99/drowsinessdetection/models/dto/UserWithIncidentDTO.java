package com.prophet99.drowsinessdetection.models.dto;

import java.time.LocalDateTime;

public class UserWithIncidentDTO {
  private String dni;
  private String name;
  private String lastName;
  private String email;
  private String cellphone;
  private Boolean active;
  private Integer incidents;
  private LocalDateTime lastIncident;

  public UserWithIncidentDTO() {
  }

  public UserWithIncidentDTO(String dni, String name, String lastName, String email, String cellphone, Boolean active, Integer incidents, LocalDateTime lastIncident) {
    this.dni = dni;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.cellphone = cellphone;
    this.active = active;
    this.incidents = incidents;
    this.lastIncident = lastIncident;
  }

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

  public Integer getIncidents() {
    return incidents;
  }

  public void setIncidents(Integer incidents) {
    this.incidents = incidents;
  }

  public LocalDateTime getLastIncident() {
    return lastIncident;
  }

  public void setLastIncident(LocalDateTime lastIncident) {
    this.lastIncident = lastIncident;
  }
}
