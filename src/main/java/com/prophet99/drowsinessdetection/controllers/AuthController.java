package com.prophet99.drowsinessdetection.controllers;

import com.prophet99.drowsinessdetection.models.requests.AuthRequest;
import com.prophet99.drowsinessdetection.utils.MessageResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(value = "")
  public ResponseEntity<MessageResponseUtil> authByUserAndPassword(@RequestBody AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil("Logueado con éxito", HttpStatus.OK.value()),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<MessageResponseUtil>(
        new MessageResponseUtil(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()),
        HttpStatus.UNAUTHORIZED
      );
    }
  }
}
