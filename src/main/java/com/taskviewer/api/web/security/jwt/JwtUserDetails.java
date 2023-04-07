package com.taskviewer.api.web.security.jwt;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskviewer.api.model.User;
import com.taskviewer.api.postgres.PgUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

  private User user;
  private boolean enabled;
  private Collection<? extends GrantedAuthority> authorities;

  public JwtUserDetails(User user) {
    List<String> roles = Collections.singletonList(user.role());
    this.user = user;
    this.enabled = true;
    this.authorities = GrantedAuthorityMapper.mapToGrantedAuthority(roles);
  }

  public JwtUserDetails(Long id, String username, List<? extends GrantedAuthority> authorities) {
    String role = authorities.get(0).getAuthority();
    User user = PgUser.builder()
            .id(id)
            .username(username)
            .role(role)
            .build();
    this.user = user;
    this.enabled = true;
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}
