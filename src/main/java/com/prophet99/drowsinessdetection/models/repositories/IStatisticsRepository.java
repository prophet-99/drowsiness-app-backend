package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IStatisticsRepository extends MongoRepository<Statistics, String> {
}
