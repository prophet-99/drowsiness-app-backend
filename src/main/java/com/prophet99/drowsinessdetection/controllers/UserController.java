package com.prophet99.drowsinessdetection.controllers;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;
import com.prophet99.drowsinessdetection.services.IUserService;
import com.prophet99.drowsinessdetection.utils.MessageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users")
public class UserController {
  private final IUserService userService;

  @Autowired
  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/active/{areActive}")
  public ResponseEntity<?> findAllUsers(
    @PathVariable(value = "areActive") Boolean areActive,
    @RequestParam(value = "search", required = false) String searchParam
  ) {
    try {
      return new ResponseEntity<List<UserWithIncidentDTO>>(
        userService.findAll(areActive, searchParam),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/dni/{dni}")
  public ResponseEntity<?> findByUserDni(@PathVariable(value = "dni") String dni) {
    try {
      User userFound = userService.findByDni(dni);
      if (Objects.isNull(userFound)) {
        return new ResponseEntity<MessageResponseUtil>(
          new MessageResponseUtil(
            String.format("No existe un usuario con el DNI: %s", dni),
            HttpStatus.NOT_FOUND.value()
          ),
          HttpStatus.NOT_FOUND
        );
      }
      return new ResponseEntity<User>(userFound, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PostMapping(value = "")
  public ResponseEntity<?> saveUser(@RequestBody User user) {
    try {
      if (Objects.isNull(userService.findByDni(user.getDni()))) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
      }
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(
          String.format("Ya existe un usuario con el DNI: %s", user.getDni()),
          HttpStatus.CONFLICT.value()
        ),
        HttpStatus.CONFLICT
      );
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PutMapping(value = "")
  public ResponseEntity<?> updateUser(@RequestBody User user) {
    try {
      if (Objects.isNull(userService.findByDni(user.getDni()))) {
        return new ResponseEntity<MessageResponseUtil>(
          new MessageResponseUtil(
            String.format("No existe un usuario con el DNI: %s", user.getDni()),
            HttpStatus.CONFLICT.value()
          ),
          HttpStatus.CONFLICT
        );
      }
      return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @DeleteMapping(value = "/dni/{dni}")
  public ResponseEntity<?> disableUser(@PathVariable(value = "dni") String dni) {
    try {
      if (userService.disable(dni)) {
        return new ResponseEntity<MessageResponseUtil>(
          new MessageResponseUtil(
            String.format("Usuario con DNI: %s, deshabilitado con éxito", dni),
            HttpStatus.OK.value()
          ),
          HttpStatus.OK
        );
      }
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(
          String.format("No se pudo deshabilitar al usuario con DNI: %s", dni),
          HttpStatus.CONFLICT.value()
        ),
        HttpStatus.CONFLICT
      );
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
