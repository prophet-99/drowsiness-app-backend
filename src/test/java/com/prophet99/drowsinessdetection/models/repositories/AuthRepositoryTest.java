package com.prophet99.drowsinessdetection.models.repositories;

import com.prophet99.drowsinessdetection.models.documents.Auth;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class AuthRepositoryTest {
  private final IAuthRepository authRepository;

  @Autowired
  AuthRepositoryTest(IAuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Test
  void testFindByGeneralUsername() {
    // GIVEN
    String username = "prophet";
    // WHEN
    Auth userAuth = authRepository.findByGeneralUsername(username);
    // THEN
    Assertions.assertThat(userAuth).isNotNull();
    Assertions.assertThat(userAuth.getGeneralPassword()).isNotEmpty();
  }
}
