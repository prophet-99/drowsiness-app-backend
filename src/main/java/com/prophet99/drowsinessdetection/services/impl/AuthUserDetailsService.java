package com.prophet99.drowsinessdetection.services.impl;

import com.prophet99.drowsinessdetection.models.documents.Auth;
import com.prophet99.drowsinessdetection.models.repositories.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthUserDetailsService implements UserDetailsService {
  private final IAuthRepository authRepository;

  @Autowired
  public AuthUserDetailsService(IAuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Auth authUser = authRepository.findByGeneralUsername(username);
    if (Objects.isNull(authUser)) throw new UsernameNotFoundException("No existe el usuario: " + username);

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

    return new User(authUser.getGeneralUsername(), authUser.getGeneralPassword(), authorities);
  }
}
