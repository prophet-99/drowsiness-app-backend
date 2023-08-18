package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;

import java.util.List;

public interface ICustomUserRepository {
  List<UserWithIncidentDTO> findAll(Boolean areActive, String searchParam);
  User save(User user);
  Boolean disable(String dni);
}
