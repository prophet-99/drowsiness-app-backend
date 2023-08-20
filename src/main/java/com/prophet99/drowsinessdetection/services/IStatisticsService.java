package com.prophet99.drowsinessdetection.services;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;

import java.util.List;

public interface IStatisticsService {
  Statistics save(Statistics statistics);
  List<StatisticsWithUserDTO> findAll(String searchParam);
  List<StatisticsWithUserDTO> findByUserDNI(String userDNI, String searchParam);
  StatisticsWithUserDTO findById(String id);
}
