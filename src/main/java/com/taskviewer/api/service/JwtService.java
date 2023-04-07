package com.taskviewer.api.service;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {

  JwtToken generate(JwtToken.TokenType tokenType, User user);

  Claims parse(JwtToken token);

  Authentication getAuthentication(JwtToken token);

  boolean isTokenExpired(JwtToken token);

  boolean isTokenType(JwtToken token, JwtToken.TokenType type);
}
