package com.taskviewer.api.service.impl;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.service.JwtService;
import com.taskviewer.api.service.props.JwtProperties;
import com.taskviewer.api.web.security.jwt.JwtUserDetailsFactory;
import com.taskviewer.api.web.security.jwt.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtProperties properties;
  private final JwtUserDetailsService userDetailsService;
  private Key key;

  @PostConstruct
  private void postConstruct() {
    key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
  }

  @Override
  public JwtToken generate(JwtToken.TokenType tokenType, User user) {
    return switch (tokenType) {
      case ACCESS -> generateAccessToken(user);
      case REFRESH -> generateRefreshToken(user);
    };
  }

  private JwtToken generateAccessToken(User user) {
    final Instant expiration = Instant.now()
            .plus(properties.getAccess(), ChronoUnit.MINUTES);
    String token = Jwts.builder()
            .setSubject(user.username())
            .claim("id", user.id())
            .claim("type", JwtToken.TokenType.ACCESS.name())
            .claim("role", user.role())
            .setExpiration(Date.from(expiration))
            .signWith(key)
            .compact();
    return new JwtToken(token);
  }

  private JwtToken generateRefreshToken(User user) {
    final Instant expiration = Instant.now()
            .plus(properties.getAccess(), ChronoUnit.HOURS);
    String token = Jwts.builder()
            .setSubject(user.username())
            .claim("id", user.id())
            .claim("type", JwtToken.TokenType.REFRESH.name())
            .claim("role", user.role())
            .setExpiration(Date.from(expiration))
            .signWith(key)
            .compact();
    return new JwtToken(token);
  }

  @Override
  public Claims parse(JwtToken token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token.getToken())
            .getBody();
  }

  @Override
  public Authentication getAuthentication(JwtToken token) {
    Claims claims = parse(token);
    JwtUserDetailsFactory factory = new JwtUserDetailsFactory(claims);
    UserDetails userDetails = userDetailsService.loadUserByUsername(
            factory.userDetails().getUsername()
    );
    return new UsernamePasswordAuthenticationToken(
            userDetails,
            "",
            userDetails.getAuthorities());
  }

  @Override
  public boolean isTokenExpired(JwtToken token) {
    try {
      Jws<Claims> claims = Jwts
              .parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token.getToken());
      return claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return true;
    }
  }

  @Override
  public boolean isTokenType(JwtToken token, JwtToken.TokenType type) {
    Claims claims = parse(token);
    return Objects.equals(claims.get("type"), type.name());
  }
}
