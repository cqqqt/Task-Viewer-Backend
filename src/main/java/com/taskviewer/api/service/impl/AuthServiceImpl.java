package com.taskviewer.api.service.impl;

import com.taskviewer.api.model.Authentication;
import com.taskviewer.api.model.AuthenticationException;
import com.taskviewer.api.model.User;
import com.taskviewer.api.model.UserAlreadyExistsException;
import com.taskviewer.api.model.jwt.JwtRs;
import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.postgres.PgUser;
import com.taskviewer.api.service.AuthService;
import com.taskviewer.api.service.JwtService;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.security.jwt.JwtUserDetailsFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JwtRs login(Authentication authentication) {
    User user = userService.byUsername(authentication.getUsername());
    String password = userService.password(user);
    if (!passwordEncoder.matches(authentication.getPassword(), password)) {
      throw new AuthenticationException("wrong password");
    }
    JwtToken accessToken = jwtService.generate(JwtToken.TokenType.ACCESS, user);
    JwtToken refreshToken = jwtService.generate(JwtToken.TokenType.REFRESH, user);
    return new JwtRs(accessToken, refreshToken);
  }

  @Override
  public JwtRs refresh(JwtToken jwtToken) {
    Claims claims = jwtService.parse(jwtToken);
    JwtUserDetailsFactory userDetailsFactory = new JwtUserDetailsFactory(claims);
    UserDetails userDetails = userDetailsFactory.userDetails();
    if (!jwtService.isTokenType(jwtToken, JwtToken.TokenType.REFRESH)) {
      throw new AuthenticationException("invalid refresh token");
    }
    User user = userService.byUsername(userDetails.getUsername());
    JwtToken accessToken = jwtService.generate(JwtToken.TokenType.ACCESS, user);
    JwtToken refreshToken = jwtService.generate(JwtToken.TokenType.REFRESH, user);
    return new JwtRs(accessToken, refreshToken);
  }

  @Override
  public User register(RqUser user) {
    if (userService.userExists(user.email(), user.username())) {
      throw new UserAlreadyExistsException("User with email %s or username %s already exists"
        .formatted(
          user.email(),
          user.username()
        ));
    }
    PgUser userToAdd = PgUser.builder()
      .username(user.username())
      .email(user.email())
      .role("ROLE_USER")
      .firstname(user.firstname())
      .lastname(user.lastname())
      .created(LocalDateTime.now())
      .password(passwordEncoder.encode(user.password()))
      .build();
    return userService.with(userToAdd);
  }
}
