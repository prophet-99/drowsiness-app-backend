package com.prophet99.drowsinessdetection.services;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;
import com.prophet99.drowsinessdetection.models.repositories.ICustomUserRepository;
import com.prophet99.drowsinessdetection.models.repositories.IUserRepository;
import com.prophet99.drowsinessdetection.services.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private ICustomUserRepository customUserRepository;
  @Mock
  private IUserRepository userRepository;
  @InjectMocks
  private UserServiceImpl userServiceImpl;
  private static User userMock;

  @BeforeAll
  static void beforeAll() {
    // SET STATISTICS MOCK
    userMock = new User();
    userMock.setDni("75497209");
    userMock.setFullName("Alexander Jhoel Avila Briones");
    userMock.setName("Alexander Jhoel");
    userMock.setLastName("Avila Briones");
    userMock.setCellphone("932265652");
    userMock.setEmail("alexanderavilab@gmail.com");
    userMock.setActive(true);
  }

  @Test
  void save() {
    // GIVEN
    BDDMockito.given(customUserRepository.save(userMock)).willReturn(userMock);
    // WHEN
    User userSave = userServiceImpl.save(userMock);
    // THEN
    Assertions.assertThat(userSave).isNotNull();
  }

  @Test
  void findAll() {
    UserWithIncidentDTO userWithIncidentDTOSc = new UserWithIncidentDTO();
    // GIVEN
    BDDMockito.given(customUserRepository.findAll(true, "")).willReturn(List.of(userWithIncidentDTOSc));
    // WHEN
    List<UserWithIncidentDTO> userWithIncidentDTOList = userServiceImpl.findAll(true, "");
    // THEN
    Assertions.assertThat(userWithIncidentDTOList).isNotNull().hasSize(1);
  }

  @Test
  void findByDni() {
    // GIVEN
    BDDMockito.given(userRepository.findById(userMock.getDni())).willReturn(Optional.of(userMock));
    // WHEN
    User userFind = userServiceImpl.findByDni(userMock.getDni());
    // THEN
    Assertions.assertThat(userFind).isNotNull();
  }

  @Test
  void disable() {
    // GIVEN
    BDDMockito.given(customUserRepository.disable(userMock.getDni())).willReturn(true);
    // WHEN
    Boolean userDisable = userServiceImpl.disable(userMock.getDni());
    // THEN
    Assertions.assertThat(userDisable).isTrue();
  }
}
