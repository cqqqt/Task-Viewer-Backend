package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.User;
import io.jsonwebtoken.Claims;

import java.util.Collections;

public class JwtUserDetailsFactory {

  public static JwtUserDetails create(User user) {
    return new JwtUserDetails(user);
  }

  public static JwtUserDetails create(Claims claims) {
    String role = claims.get("role", String.class);
    return new JwtUserDetails(
            claims.get("id", Long.class),
            claims.getSubject(),
            GrantedAuthorityMapper.mapToGrantedAuthority(Collections.singletonList(role)));
  }
}
