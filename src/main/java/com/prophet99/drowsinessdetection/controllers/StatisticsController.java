package com.prophet99.drowsinessdetection.controllers;

import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;
import com.prophet99.drowsinessdetection.models.vo.StatisticsVO;
import com.prophet99.drowsinessdetection.services.IStatisticsService;
import com.prophet99.drowsinessdetection.services.IUserService;
import com.prophet99.drowsinessdetection.utils.MessageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {
  private final SimpMessagingTemplate messagingTemplate;
  private final IStatisticsService statisticsService;
  private final IUserService userService;

  @Autowired
  public StatisticsController(SimpMessagingTemplate messagingTemplate, IStatisticsService statisticsService, IUserService userService) {
    this.messagingTemplate = messagingTemplate;
    this.statisticsService = statisticsService;
    this.userService = userService;
  }

  @GetMapping(value = "")
  public ResponseEntity<?> findAllStatistics(@RequestParam(value = "search", required = false) String searchParam) {
    try {
      return new ResponseEntity<>(statisticsService.findAll(searchParam), HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping("/userDNI/{userDNI}")
  public ResponseEntity<?> finStatisticsByUserDni(
    @PathVariable("userDNI") String userDNI,
    @RequestParam(value = "search", required = false) String searchParam
  ) {
    try {
      return new ResponseEntity<>(
        statisticsService.findByUserDNI(userDNI, searchParam), HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/id/{id}")
  public ResponseEntity<?> findByStatisticsId(@PathVariable(value = "id") String id) {
    try {
      StatisticsWithUserDTO stWithUser = statisticsService.findById(id);
      if (Objects.isNull(stWithUser)){
        return new ResponseEntity<>(
          new MessageResponseUtil(
            String.format("No existe una estadística con el id: %s", id),
            HttpStatus.NOT_FOUND.value()
          ),
          HttpStatus.NOT_FOUND
        );
      }
      return new ResponseEntity<>(stWithUser, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PostMapping(value = "")
  public ResponseEntity<?> saveStatistics(@RequestBody StatisticsVO statisticsVO) {
    try {
      if (Objects.isNull(statisticsService.findById(statisticsVO.getId()))) {
        if (Objects.isNull(userService.findByDni(statisticsVO.getUserDNI()))) {
          return new ResponseEntity<>(
            new MessageResponseUtil(
              String.format("No existe un usuario con DNI: %s", statisticsVO.getUserDNI()),
              HttpStatus.NOT_FOUND.value()
            ),
            HttpStatus.NOT_FOUND
          );
        }
        // WEBSOCKET
        messagingTemplate.convertAndSend(
          "/topic/statistics",
          "Nueva incidencia disponible"
        );
        return new ResponseEntity<>(statisticsService.save(statisticsVO.getStatisticsEntity()), HttpStatus.CREATED);
      }
      return new ResponseEntity<>(
        new MessageResponseUtil(
          String.format("Ya existe una estadística con el id: %s", statisticsVO.getId()),
          HttpStatus.CONFLICT.value()
        ),
        HttpStatus.CONFLICT
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
