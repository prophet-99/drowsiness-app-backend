package com.prophet99.drowsinessdetection.models.mappers;

import java.time.LocalDateTime;
import java.util.List;

public class StatisticsWithUserMapper {
  private String id;
  private String userDNI;
  private Double longitude;
  private Double latitude;
  private LocalDateTime registerDate;
  private List<StatisticsWithUserMapper.User> user;
  public class User {
    private String name;
    private String lastName;
    private String email;
    private String cellphone;
    private List<Boolean> active;

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

    public List<Boolean> getActive() {
      return active;
    }

    public void setActive(List<Boolean> active) {
      this.active = active;
    }
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

  public List<StatisticsWithUserMapper.User> getUser() {
    return user;
  }

  public void setUser(List<StatisticsWithUserMapper.User> user) {
    this.user = user;
  }
}
