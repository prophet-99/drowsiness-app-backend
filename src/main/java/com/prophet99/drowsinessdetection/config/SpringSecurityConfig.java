package com.prophet99.drowsinessdetection.config;

import com.prophet99.drowsinessdetection.services.impl.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {
  private final AuthUserDetailsService authUserDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;

  @Autowired
  public SpringSecurityConfig(
    AuthUserDetailsService authUserDetailsService,
    AuthenticationConfiguration authenticationConfiguration
  ) {
    this.authUserDetailsService = authUserDetailsService;
    this.authenticationConfiguration = authenticationConfiguration;
  }

  @Autowired
  public void userDetailsService(AuthenticationManagerBuilder authenticationMB) throws Exception {
    authenticationMB.userDetailsService(authUserDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authHttp -> authHttp.anyRequest().permitAll())
      .httpBasic(Customizer.withDefaults());

    return httpSecurity.build();
  }
}
