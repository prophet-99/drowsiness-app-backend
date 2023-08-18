package com.prophet99.drowsinessdetection.utils;

public class MessageResponseUtil {
  private String message;
  private Integer statusCode;

  public MessageResponseUtil(String message, Integer statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }
}
