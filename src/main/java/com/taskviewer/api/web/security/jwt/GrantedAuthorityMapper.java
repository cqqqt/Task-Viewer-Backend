package com.taskviewer.api.web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GrantedAuthorityMapper {

  public static List<GrantedAuthority> mapToGrantedAuthority(List<String> userRoles) {
    return userRoles.stream()
      .filter(Objects::nonNull)
      .map(authority ->
        new SimpleGrantedAuthority(
          authority.substring(5)
        )
      )
      .collect(Collectors.toList());
  }
}
