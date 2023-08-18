package com.prophet99.drowsinessdetection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public NotificationController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("/notify.statistics")
  @SendTo("/topic/statistics")
  public String notifyStatisticsRegistered() {
    return "Nueva incidencia disponible";
  }
}
