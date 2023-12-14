package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;
import com.prophet99.drowsinessdetection.models.repositories.impl.CustomStatisticsRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataMongoTest
class CustomStatisticsRepositoryTest {
  private final ICustomStatisticsRepository customStatisticsRepository;
  private static Statistics statisticsMock;
  private final MongoTemplate mongoTemplate;

  @Autowired
  CustomStatisticsRepositoryTest(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
    this.customStatisticsRepository = new CustomStatisticsRepositoryImpl(mongoTemplate);
  }

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
  void findAll() {
    // GIVEN
    customStatisticsRepository.save(statisticsMock);
    // WHEN
    List<StatisticsWithUserDTO> statisticsList = customStatisticsRepository.findAll("");
    // THEN
    Assertions.assertThat(statisticsList).isNotNull();
    Assertions.assertThat(statisticsList.get(0).getId()).isEqualTo(statisticsMock.getId());
  }

  @Test
  void findByUserDNI() {
    // GIVEN
    // WHEN
    List<StatisticsWithUserDTO> statisticsFindList = customStatisticsRepository.findByUserDNI(statisticsMock.getUserDNI(), "");
    // THEN
    Assertions.assertThat(statisticsFindList).isNotNull();
    Assertions.assertThat(statisticsFindList.get(0).getUserDNI()).isEqualTo(statisticsMock.getUserDNI());
  }

  @Test
  void findById() {
    // GIVEN
    // WHEN
    StatisticsWithUserDTO statisticsFind = customStatisticsRepository.findById(statisticsMock.getId()).orElse(null);
    // THEN
    Assertions.assertThat(statisticsFind).isNotNull();
    Assertions.assertThat(statisticsFind.getId()).isEqualTo(statisticsMock.getId());
  }

  @Test
  void save() {
    // GIVEN
    // WHEN
    Statistics statisticsSave = customStatisticsRepository.save(statisticsMock);
    // THEN
    Assertions.assertThat(statisticsSave).isNotNull();
    Assertions.assertThat(statisticsSave.getId()).isEqualTo(statisticsMock.getId());
  }

  @Test
  void delete() {
    // GIVEN
    // WHEN
    customStatisticsRepository.deleteById(statisticsMock.getId());
    Optional<StatisticsWithUserDTO> statisticsDelete = customStatisticsRepository.findById(statisticsMock.getId());
    // THEN
    Assertions.assertThat(statisticsDelete).isEmpty();
  }
}
