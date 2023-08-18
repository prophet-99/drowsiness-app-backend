package com.prophet99.drowsinessdetection.models.dto;

import java.time.LocalDateTime;

public class StatisticsWithUserDTO {
  private String id;
  private String userDNI;
  private Double longitude;
  private Double latitude;
  private LocalDateTime registerDate;
  private User user;
  public class User {
    private String name;
    private String lastName;
    private String email;
    private String cellphone;
    private Boolean active;

    public User(String name, String lastName, String email, String cellphone, Boolean active) {
      this.name = name;
      this.lastName = lastName;
      this.email = email;
      this.cellphone = cellphone;
      this.active = active;
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

    public String getCellphone() { return cellphone; }

    public void setCellphone(String cellphone) { this.cellphone = cellphone; }

    public Boolean getActive() {
      return active;
    }

    public void setActive(Boolean active) {
      this.active = active;
    }
  }

  public StatisticsWithUserDTO() {
  }

  public StatisticsWithUserDTO(String id, String userDNI, Double longitude, Double latitude, LocalDateTime registerDate, User user) {
    this.id = id;
    this.userDNI = userDNI;
    this.longitude = longitude;
    this.latitude = latitude;
    this.registerDate = registerDate;
    this.user = user;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserDNI() {
    return userDNI;
  }

  public void setUserDNI(String userDNI) {
    this.userDNI = userDNI;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public LocalDateTime getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(LocalDateTime registerDate) {
    this.registerDate = registerDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
