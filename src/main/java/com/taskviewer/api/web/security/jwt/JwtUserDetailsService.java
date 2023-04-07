package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final Users users;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = users.user(email);
    return JwtUserDetailsFactory.create(user);
  }
}
