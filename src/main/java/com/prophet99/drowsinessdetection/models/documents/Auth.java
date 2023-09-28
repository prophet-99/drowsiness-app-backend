package com.prophet99.drowsinessdetection.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth")
public class Auth {
  @Id
  private String id;
  public String generalUsername;
  public String generalPassword;

  public String getGeneralUsername() {
    return generalUsername;
  }

  public void setGeneralUsername(String generalUsername) {
    this.generalUsername = generalUsername;
  }

  public String getGeneralPassword() {
    return generalPassword;
  }

  public void setGeneralPassword(String generalPassword) {
    this.generalPassword = generalPassword;
  }
}
