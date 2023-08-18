package com.prophet99.drowsinessdetection.services.impl;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;
import com.prophet99.drowsinessdetection.models.repositories.ICustomUserRepository;
import com.prophet99.drowsinessdetection.models.repositories.IUserRepository;
import com.prophet99.drowsinessdetection.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
  private final IUserRepository userRepository;
  private final ICustomUserRepository customUserRepository;

  @Autowired
  public UserServiceImpl(IUserRepository userRepository, ICustomUserRepository customUserRepository) {
    this.userRepository = userRepository;
    this.customUserRepository = customUserRepository;
  }

  @Transactional
  @Override
  public User save(User user){
    return customUserRepository.save(user);
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserWithIncidentDTO> findAll(Boolean areActive, String searchParam) {
    return customUserRepository.findAll(areActive, searchParam);
  }

  @Transactional(readOnly = true)
  @Override
  public User findByDni(String dni) {
    return userRepository.findById(dni).orElse(null);
  }

  @Transactional
  @Override
  public Boolean disable(String dni) {
    return customUserRepository.disable(dni);
  }
}
