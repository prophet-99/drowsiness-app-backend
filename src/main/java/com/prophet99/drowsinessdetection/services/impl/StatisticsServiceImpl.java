package com.prophet99.drowsinessdetection.services.impl;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;
import com.prophet99.drowsinessdetection.models.mappers.StatisticsWithUserMapper;
import com.prophet99.drowsinessdetection.models.repositories.ICustomStatisticsRepository;
import com.prophet99.drowsinessdetection.models.repositories.IStatisticsRepository;
import com.prophet99.drowsinessdetection.services.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatisticsServiceImpl implements IStatisticsService {
  private final IStatisticsRepository statisticsRepository;
  private final ICustomStatisticsRepository customStatisticsRepository;

  @Autowired
  public StatisticsServiceImpl(IStatisticsRepository statisticsRepository, ICustomStatisticsRepository customStatisticsRepository) {
    this.statisticsRepository = statisticsRepository;
    this.customStatisticsRepository = customStatisticsRepository;
  }

  @Transactional
  @Override
  public Statistics save(Statistics statistics) {
    return customStatisticsRepository.save(statistics);
  }

  @Transactional(readOnly = true)
  @Override
  public List<StatisticsWithUserDTO> findAll(String searchParam) {
    return customStatisticsRepository.findAll(searchParam);
  }

  @Transactional(readOnly = true)
  @Override
  public StatisticsWithUserDTO findById(String id) {
    return customStatisticsRepository.findById(id).orElse(null);
  }
}
