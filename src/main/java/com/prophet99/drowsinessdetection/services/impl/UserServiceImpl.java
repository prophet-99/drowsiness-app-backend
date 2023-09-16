package com.prophet99.drowsinessdetection.services.impl;

import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;
import com.prophet99.drowsinessdetection.models.repositories.ICustomUserRepository;
import com.prophet99.drowsinessdetection.models.repositories.IUserRepository;
import com.prophet99.drowsinessdetection.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

  @Transactional
  @Override
  public void savePhoto(String dni, MultipartFile file) throws IOException {
    Path uploadDir = Paths.get("userphotos");
    if (Files.notExists(uploadDir)) Files.createDirectory(uploadDir);
    String fileExtension = file.getOriginalFilename().split("\\.")[
      file.getOriginalFilename().split("\\.").length - 1
    ];
    String fileName = dni + "." + fileExtension;
    Path filePath = Paths.get(uploadDir.toString(), fileName);

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
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
