package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;

import java.util.List;
import java.util.Optional;

public interface ICustomStatisticsRepository {
  List<StatisticsWithUserDTO> findAll(String searchParam);
  List<StatisticsWithUserDTO> findByUserDNI(String userDNI, String searchParam);
  Optional<StatisticsWithUserDTO> findById(String id);
  Statistics save(Statistics statistics);
  void deleteById(String id);
}
