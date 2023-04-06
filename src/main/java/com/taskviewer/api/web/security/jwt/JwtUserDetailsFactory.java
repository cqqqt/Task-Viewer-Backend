package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtUserDetailsFactory {

  public static JwtUserDetails create(User user) {
    return new JwtUserDetails(
            user.id(),
            user.username(),
            user.email(),
            null,
            true,
            mapToGrantedAuthority(Collections.singletonList(user.role())));
  }

  public static JwtUserDetails create(Claims claims) {
    String role = claims.get("role", String.class);
    return new JwtUserDetails(
            claims.get("id", Long.class),
            claims.getSubject(),
            null,
            null,
            true,
            mapToGrantedAuthority(Collections.singletonList(role)));
  }


  private static List<GrantedAuthority> mapToGrantedAuthority(List<String> userRoles) {
    return userRoles.stream()
            .filter(Objects::nonNull)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
  }
}
