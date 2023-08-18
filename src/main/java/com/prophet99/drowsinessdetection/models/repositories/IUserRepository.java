package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
  @Query("{'active': ?0}")
  List<User> findAll(Boolean active);
  @Query("{'_id': ?0, 'active':  true}")
  @Override
  Optional<User> findById(String dni);
}
