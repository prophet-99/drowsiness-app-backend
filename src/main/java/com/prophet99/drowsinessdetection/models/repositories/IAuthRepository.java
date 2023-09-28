package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAuthRepository extends MongoRepository<Auth, String> {
  Auth findByGeneralUsername(String username);
}
