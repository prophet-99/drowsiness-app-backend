package com.prophet99.drowsinessdetection.models.repositories.impl;

import com.mongodb.client.result.UpdateResult;
import com.prophet99.drowsinessdetection.models.documents.User;
import com.prophet99.drowsinessdetection.models.dto.UserWithIncidentDTO;
import com.prophet99.drowsinessdetection.models.repositories.ICustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class CustomUserRepositoryImpl implements ICustomUserRepository {
  private final MongoTemplate mongoTemplate;

  @Autowired
  public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<UserWithIncidentDTO> findAll(Boolean areActive, String searchParam) {
    Criteria criteriaSearch = new Criteria();
    if (!Objects.isNull(searchParam) && !searchParam.isEmpty()) {
      String regex = "(?i).*" + Arrays.stream(searchParam.split("\\s+"))
        .map(Pattern::quote)
        .collect(Collectors.joining(".*")) + ".*";
      criteriaSearch.orOperator(
        Criteria.where("_id").regex(regex),
        Criteria.where("_fullName").regex(regex)
      );
    }
    AggregationOperation matchActiveUsers = Aggregation.match(
      Criteria.where("active").is(areActive).andOperator(criteriaSearch)
    );

    LookupOperation lookup = LookupOperation.newLookup()
      .from("statistics")
      .localField("_id")
      .foreignField("userDNI")
      .as("statistics");

    AggregationOperation unwindStatistics = Aggregation.unwind("statistics",true);

    AggregationOperation groupById = Aggregation.group("_id")
      .first("name").as("name")
      .first("lastName").as("lastName")
      .first("email").as("email")
      .first("cellphone").as("cellphone")
      .first("active").as("active")
      .count().as("totalCount")
      .max("statistics.registerDate").as("lastRegisterDate");

    ProjectionOperation project = Aggregation.project()
      .andExpression("_id").as("dni")
      .andExpression("name").as("name")
      .andExpression("lastName").as("lastName")
      .andExpression("email").as("email")
      .andExpression("cellphone").as("cellphone")
      .andExpression("active").as("active")
      .andExpression("totalCount").as("incidents")
      .andExpression("lastRegisterDate").as("lastIncident");

    Aggregation aggregation = Aggregation.newAggregation(matchActiveUsers, lookup, unwindStatistics, groupById, project);
    AggregationResults<UserWithIncidentDTO> results = mongoTemplate.aggregate(
      aggregation,
      "users",
      UserWithIncidentDTO.class
    );
    return results.getMappedResults().stream().map((user) -> {
      if (Objects.isNull(user.getLastIncident())) user.setIncidents(0);
      return user;
    }).collect(Collectors.toList());
  }

  @Override
  public User save(User user) {
    user.setActive(true);
    user.setFullName(user.getName() + " " + user.getLastName());
    return mongoTemplate.save(user);
  }

  @Override
  public Boolean disable(String dni) {
    Query query = new Query(Criteria.where("_id").is(dni));
    Update update = new Update();
    update.set("active", false);

    UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
    return (result.getMatchedCount() > 0) ? true : false;
  }
}
