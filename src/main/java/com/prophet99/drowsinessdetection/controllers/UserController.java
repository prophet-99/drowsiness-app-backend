package com.prophet99.drowsinessdetection.controllers;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.vo.UserVO;
import com.prophet99.drowsinessdetection.services.IUserService;
import com.prophet99.drowsinessdetection.utils.MessageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
      return new ResponseEntity<>(
        userService.findAll(areActive, searchParam),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(
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
        return new ResponseEntity<>(
          new MessageResponseUtil(
            String.format("No existe un usuario con el DNI: %s", dni),
            HttpStatus.NOT_FOUND.value()
          ),
          HttpStatus.NOT_FOUND
        );
      }
      return new ResponseEntity<>(userFound, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @Secured(value = "ROLE_ADMIN")
  @PostMapping(value = "")
  public ResponseEntity<?> saveUser(@RequestBody UserVO userVO) {
    try {
      if (Objects.isNull(userService.findByDni(userVO.getDni()))) {
        return new ResponseEntity<>(userService.save(userVO.getUserEntity()), HttpStatus.CREATED);
      }
      return new ResponseEntity<>(
        new MessageResponseUtil(
          String.format("Ya existe un usuario con el DNI: %s", userVO.getDni()),
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

  @Secured(value = "ROLE_ADMIN")
  @PostMapping(value = "/{dni}/photo")
  public ResponseEntity<?> saveUserPhoto(
    @PathVariable(value = "dni") String dni,
    @RequestParam(value = "file") MultipartFile file
  ) {
    try {
      if (Objects.isNull(userService.findByDni(dni))) return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(
          String.format("No existe un usuario con el DNI: %s", dni),
          HttpStatus.CONFLICT.value()
        ),
        HttpStatus.CONFLICT
      );
      userService.savePhoto(dni, file);
      return new ResponseEntity<>(
        new MessageResponseUtil("Foto guardada con éxito",  HttpStatus.CREATED.value()),
        HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/photo/{fileName:.+}")
  public ResponseEntity<?> getPhoto(@PathVariable(value = "fileName") String fileName) {
    Path filePath = Paths.get("userphotos").resolve(fileName);
    Resource resource;
    try {
      resource = new UrlResource(filePath.toUri());
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(switch (fileName.split("\\.")[1]) {
        case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
        case "png" -> MediaType.IMAGE_PNG;
        default -> MediaType.ALL;
      });
      return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    } catch (MalformedURLException ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @Secured(value = "ROLE_ADMIN")
  @PutMapping(value = "")
  public ResponseEntity<?> updateUser(@RequestBody UserVO userVO) {
    try {
      if (Objects.isNull(userService.findByDni(userVO.getDni()))) {
        return new ResponseEntity<>(
          new MessageResponseUtil(
            String.format("No existe un usuario con el DNI: %s", userVO.getDni()),
            HttpStatus.CONFLICT.value()
          ),
          HttpStatus.CONFLICT
        );
      }
      return new ResponseEntity<>(userService.save(userVO.getUserEntity()), HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @Secured(value = "ROLE_ADMIN")
  @DeleteMapping(value = "/dni/{dni}")
  public ResponseEntity<?> disableUser(@PathVariable(value = "dni") String dni) {
    try {
      if (userService.disable(dni)) {
        return new ResponseEntity<>(
          new MessageResponseUtil(
            String.format("Usuario con DNI: %s, deshabilitado con éxito", dni),
            HttpStatus.OK.value()
          ),
          HttpStatus.OK
        );
      }
      return new ResponseEntity<>(
        new MessageResponseUtil(
          String.format("No se pudo deshabilitar al usuario con DNI: %s", dni),
          HttpStatus.CONFLICT.value()
        ),
        HttpStatus.CONFLICT
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(
        new MessageResponseUtil(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
