package com.prophet99.drowsinessdetection.models.repositories.impl;

import com.prophet99.drowsinessdetection.models.documents.Statistics;
import com.prophet99.drowsinessdetection.models.dto.StatisticsWithUserDTO;
import com.prophet99.drowsinessdetection.models.mappers.StatisticsWithUserMapper;
import com.prophet99.drowsinessdetection.models.repositories.ICustomStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

@Repository
public class CustomStatisticsRepositoryImpl implements ICustomStatisticsRepository {
  private final MongoTemplate mongoTemplate;

  @Autowired
  public CustomStatisticsRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<StatisticsWithUserDTO> findAll(String searchParam) {
    Criteria criteriaSearch = new Criteria();
    if (!Objects.isNull(searchParam) && !searchParam.isEmpty()) {
      try {
        LocalDate localDate = LocalDate.parse(searchParam);
        ZonedDateTime startOfDay = localDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endOfDay = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault());

        criteriaSearch.andOperator(
          Criteria.where("registerDate").gte(Date.from(startOfDay.toInstant())),
          Criteria.where("registerDate").lt(Date.from(endOfDay.toInstant()))
        );
      } catch (DateTimeParseException ex) {}
    }
    SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "registerDate"));
    AggregationOperation matchSearch = Aggregation.match(criteriaSearch);
    AggregationOperation matchActiveUser = Aggregation.match(Criteria.where("user.active").is(true));

    LookupOperation lookup = LookupOperation.newLookup()
      .from("users")
      .localField("userDNI")
      .foreignField("_id")
      .as("user");
    ProjectionOperation project = Aggregation.project()
      .andExpression("_id").as("id")
      .andExpression("userDNI").as("userDNI")
      .andExpression("longitude").as("longitude")
      .andExpression("latitude").as("latitude")
      .andExpression("registerDate").as("registerDate")
      .andExpression("user.name").as("user.name")
      .andExpression("user.lastName").as("user.lastName")
      .andExpression("user.cellphone").as("user.cellphone")
      .andExpression("user.email").as("user.email")
      .andExpression("user.active").as("user.active");
    Aggregation aggregation = Aggregation.newAggregation(lookup, project, matchSearch, matchActiveUser, sortOperation);
    AggregationResults<StatisticsWithUserMapper> results = mongoTemplate.aggregate(aggregation, "statistics", StatisticsWithUserMapper.class);

    List<StatisticsWithUserMapper> rawDTOList = results.getMappedResults();
    List<StatisticsWithUserDTO> parsedDTOList = new ArrayList<>();

    rawDTOList.forEach((dtoRef) -> {
      StatisticsWithUserDTO scopeParsedDTO = new StatisticsWithUserDTO(
        dtoRef.getId(),
        dtoRef.getUserDNI(),
        dtoRef.getLongitude(),
        dtoRef.getLatitude(),
        dtoRef.getRegisterDate(),
        null
      );
      scopeParsedDTO.setUser(scopeParsedDTO.new User(
        dtoRef.getUser().get(0).getName(),
        dtoRef.getUser().get(0).getLastName(),
        dtoRef.getUser().get(0).getEmail(),
        dtoRef.getUser().get(0).getCellphone(),
        dtoRef.getUser().get(0).getActive().get(0)
      ));
      parsedDTOList.add(scopeParsedDTO);
    });
    return parsedDTOList;
  }

  @Override
  public List<StatisticsWithUserDTO> findByUserDNI(String userDNI, String searchParam) {
    Criteria criteriaSearch = new Criteria();

    if (!Objects.isNull(searchParam) && !searchParam.isEmpty()) {
      try {
        LocalDate localDate = LocalDate.parse(searchParam);
        ZonedDateTime startOfDay = localDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endOfDay = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault());

        criteriaSearch.andOperator(
          Criteria.where("registerDate").gte(Date.from(startOfDay.toInstant())),
          Criteria.where("registerDate").lt(Date.from(endOfDay.toInstant()))
        );
      } catch (DateTimeParseException ex) {}
    }
    SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "registerDate"));
    AggregationOperation matchSearch = Aggregation.match(criteriaSearch);
    AggregationOperation matchUserByDni = Aggregation.match(Criteria.where("userDNI").is(userDNI));
    AggregationOperation matchActiveUser = Aggregation.match(Criteria.where("user.active").is(true));

    LookupOperation lookup = LookupOperation.newLookup()
      .from("users")
      .localField("userDNI")
      .foreignField("_id")
      .as("user");
    ProjectionOperation project = Aggregation.project()
      .andExpression("_id").as("id")
      .andExpression("userDNI").as("userDNI")
      .andExpression("longitude").as("longitude")
      .andExpression("latitude").as("latitude")
      .andExpression("registerDate").as("registerDate")
      .andExpression("user.name").as("user.name")
      .andExpression("user.lastName").as("user.lastName")
      .andExpression("user.cellphone").as("user.cellphone")
      .andExpression("user.email").as("user.email")
      .andExpression("user.active").as("user.active");
    Aggregation aggregation = Aggregation.newAggregation(
      lookup, project, matchSearch, matchUserByDni, matchActiveUser, sortOperation
    );
    AggregationResults<StatisticsWithUserMapper> results = mongoTemplate.aggregate(aggregation, "statistics", StatisticsWithUserMapper.class);

    List<StatisticsWithUserMapper> rawDTOList = results.getMappedResults();
    List<StatisticsWithUserDTO> parsedDTOList = new ArrayList<>();

    rawDTOList.forEach((dtoRef) -> {
      StatisticsWithUserDTO scopeParsedDTO = new StatisticsWithUserDTO(
        dtoRef.getId(),
        dtoRef.getUserDNI(),
        dtoRef.getLongitude(),
        dtoRef.getLatitude(),
        dtoRef.getRegisterDate(),
        null
      );
      scopeParsedDTO.setUser(scopeParsedDTO.new User(
        dtoRef.getUser().get(0).getName(),
        dtoRef.getUser().get(0).getLastName(),
        dtoRef.getUser().get(0).getEmail(),
        dtoRef.getUser().get(0).getCellphone(),
        dtoRef.getUser().get(0).getActive().get(0)
      ));
      parsedDTOList.add(scopeParsedDTO);
    });
    return parsedDTOList;
  }

  @Override
  public Optional<StatisticsWithUserDTO> findById(String id) {
    AggregationOperation match = Aggregation.match(Criteria.where("_id").is(id));
    AggregationOperation matchActiveUser = Aggregation.match(Criteria.where("user.active").is(true));
    LookupOperation lookup = LookupOperation.newLookup()
      .from("users")
      .localField("userDNI")
      .foreignField("_id")
      .as("user");
    ProjectionOperation project = Aggregation.project()
      .andExpression("_id").as("id")
      .andExpression("userDNI").as("userDNI")
      .andExpression("longitude").as("longitude")
      .andExpression("latitude").as("latitude")
      .andExpression("registerDate").as("registerDate")
      .andExpression("user.name").as("user.name")
      .andExpression("user.lastName").as("user.lastName")
      .andExpression("user.cellphone").as("user.cellphone")
      .andExpression("user.email").as("user.email")
      .andExpression("user.active").as("user.active");
    Aggregation aggregation = Aggregation.newAggregation(match, lookup, project, matchActiveUser);
    AggregationResults<StatisticsWithUserMapper> results = mongoTemplate.aggregate(aggregation, "statistics", StatisticsWithUserMapper.class);

    StatisticsWithUserMapper rawDTO = results.getUniqueMappedResult();
    StatisticsWithUserDTO parsedDTO = null;
    if (!Objects.isNull(rawDTO)) {
      parsedDTO = new StatisticsWithUserDTO(
        rawDTO.getId(),
        rawDTO.getUserDNI(),
        rawDTO.getLongitude(),
        rawDTO.getLatitude(),
        rawDTO.getRegisterDate(),
        null
      );
      parsedDTO.setUser(parsedDTO.new User(
        rawDTO.getUser().get(0).getName(),
        rawDTO.getUser().get(0).getLastName(),
        rawDTO.getUser().get(0).getEmail(),
        rawDTO.getUser().get(0).getCellphone(),
        rawDTO.getUser().get(0).getActive().get(0)
      ));
    }
    return Optional.ofNullable(parsedDTO);
  }

  @Override
  public Statistics save(Statistics statistics) {
    // THE SERVER IS LOCATED IN US West (Oregon, USA)
    ZoneId peruZone = ZoneId.of("America/Lima");
    LocalDateTime peruDateTime = LocalDateTime.now(peruZone);
    statistics.setRegisterDate(peruDateTime);
    return mongoTemplate.save(statistics);
  }

  @Override
  public void deleteById(String id) {
    Optional<StatisticsWithUserDTO> statisticsRefOpt = findById(id);
    if (!statisticsRefOpt.isEmpty()) mongoTemplate.remove(statisticsRefOpt);
  }
}
