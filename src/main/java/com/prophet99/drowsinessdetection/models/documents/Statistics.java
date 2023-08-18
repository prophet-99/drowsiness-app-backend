package com.prophet99.drowsinessdetection.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "statistics")
public class Statistics {
  @Id
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
    return "Statistics{" +
      "id='" + id + '\'' +
      ", userDNI='" + userDNI + '\'' +
      ", longitude=" + longitude +
      ", latitude=" + latitude +
      ", registerDate=" + registerDate +
      '}';
  }
}
