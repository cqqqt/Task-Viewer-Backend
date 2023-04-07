package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.User;
import com.taskviewer.api.postgres.PgUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetailsFactory {

  private final User user;

  public JwtUserDetailsFactory(User user) {
    this.user = user;
  }

  public JwtUserDetailsFactory(Claims claims) {
    String role = claims.get("role", String.class);
    User user = PgUser.builder()
            .id(claims.get("id", Long.class))
            .username(claims.getSubject())
            .role(role)
            .build();
    this.user = user;
  }

  public UserDetails userDetails() {
    return new JwtUserDetails(user);
  }
}
