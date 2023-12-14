package com.prophet99.drowsinessdetection.models.vo;

import com.prophet99.drowsinessdetection.models.documents.Statistics;

import java.time.LocalDateTime;

public class StatisticsVO {
  private String id;
  private String userDNI;
  private Double longitude;
  private Double latitude;
  private LocalDateTime registerDate;

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

  @Override
  public String toString() {
    return "StatisticsVO{" +
      "id='" + id + '\'' +
      ", userDNI='" + userDNI + '\'' +
      ", longitude=" + longitude +
      ", latitude=" + latitude +
      ", registerDate=" + registerDate +
      '}';
  }

  public Statistics getStatisticsEntity() {
    Statistics statistics = new Statistics();
    statistics.setId(getId());
    statistics.setUserDNI(getUserDNI());
    statistics.setLongitude(getLongitude());
    statistics.setLatitude(getLatitude());
    statistics.setRegisterDate(getRegisterDate());

    return statistics;
  }
}
