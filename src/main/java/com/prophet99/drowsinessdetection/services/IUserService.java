package com.prophet99.drowsinessdetection.services;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;

import java.util.List;

public interface IUserService {
  User save(User user);
  List<UserWithIncidentDTO> findAll(Boolean areActive, String searchParam);
  User findByDni(String dni);
  Boolean disable(String dni);
}
