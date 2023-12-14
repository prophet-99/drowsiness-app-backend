package com.prophet99.drowsinessdetection.services;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;
import com.prophet99.drowsinessdetection.models.repositories.ICustomStatisticsRepository;
import com.prophet99.drowsinessdetection.services.impl.StatisticsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {
  @Mock
  private ICustomStatisticsRepository customStatisticsRepository;
  @InjectMocks
  private StatisticsServiceImpl statisticsServiceImpl;
  private static Statistics statisticsMock;

  @BeforeAll
  static void beforeAll() {
    // SET STATISTICS MOCK
    statisticsMock = new Statistics();
    statisticsMock.setUserDNI("75497209");
    statisticsMock.setRegisterDate(LocalDateTime.now());
    statisticsMock.setLatitude(-6.7);
    statisticsMock.setLongitude(-79.8);
  }

  @Test
  void save() {
    // GIVEN
    BDDMockito.given(customStatisticsRepository.save(statisticsMock)).willReturn(statisticsMock);
    // WHEN
    Statistics statisticsSave = statisticsServiceImpl.save(statisticsMock);
    // THEN
    Assertions.assertThat(statisticsSave).isNotNull();
  }

  @Test
  void findAll() {
    // GIVEN
    StatisticsWithUserDTO statisticsWithUserDTOSc = new StatisticsWithUserDTO();
    statisticsWithUserDTOSc.setUser(null);
    statisticsWithUserDTOSc.setId(statisticsMock.getId());
    statisticsWithUserDTOSc.setUserDNI(statisticsMock.getUserDNI());
    statisticsWithUserDTOSc.setLatitude(statisticsMock.getLatitude());
    statisticsWithUserDTOSc.setLongitude(statisticsMock.getLongitude());
    statisticsWithUserDTOSc.setRegisterDate(statisticsMock.getRegisterDate());
    BDDMockito.given(customStatisticsRepository.findAll("")).willReturn(List.of(statisticsWithUserDTOSc));
    // WHEN
    List<StatisticsWithUserDTO> statisticsList = statisticsServiceImpl.findAll("");
    // THEN
    Assertions.assertThat(statisticsList).isNotNull().hasSize(1);
  }

  @Test
  void findByUserDNI() {
    // GIVEN
    StatisticsWithUserDTO statisticsWithUserDTOSc = new StatisticsWithUserDTO();
    statisticsWithUserDTOSc.setUser(null);
    statisticsWithUserDTOSc.setId(statisticsMock.getId());
    statisticsWithUserDTOSc.setUserDNI(statisticsMock.getUserDNI());
    statisticsWithUserDTOSc.setLatitude(statisticsMock.getLatitude());
    statisticsWithUserDTOSc.setLongitude(statisticsMock.getLongitude());
    statisticsWithUserDTOSc.setRegisterDate(statisticsMock.getRegisterDate());
    BDDMockito.given(customStatisticsRepository.findById(statisticsMock.getUserDNI())).willReturn(Optional.of(statisticsWithUserDTOSc));
    // WHEN
    StatisticsWithUserDTO statisticsFind = statisticsServiceImpl.findById(statisticsMock.getUserDNI());
    // THEN
    Assertions.assertThat(statisticsFind).isNotNull();
    Assertions.assertThat(statisticsFind.getId()).isEqualTo(statisticsMock.getId());
  }

  @Test
  void findById() {
    // GIVEN
    StatisticsWithUserDTO statisticsWithUserDTOSc = new StatisticsWithUserDTO();
    statisticsWithUserDTOSc.setUser(null);
    statisticsWithUserDTOSc.setId(statisticsMock.getId());
    statisticsWithUserDTOSc.setUserDNI(statisticsMock.getUserDNI());
    statisticsWithUserDTOSc.setLatitude(statisticsMock.getLatitude());
    statisticsWithUserDTOSc.setLongitude(statisticsMock.getLongitude());
    statisticsWithUserDTOSc.setRegisterDate(statisticsMock.getRegisterDate());
    BDDMockito.given(customStatisticsRepository.findById(statisticsMock.getId())).willReturn(Optional.of(statisticsWithUserDTOSc));
    // WHEN
    StatisticsWithUserDTO statisticsFind = statisticsServiceImpl.findById(statisticsMock.getId());
    // THEN
    Assertions.assertThat(statisticsFind).isNotNull();
    Assertions.assertThat(statisticsFind.getId()).isEqualTo(statisticsMock.getId());
  }
}
