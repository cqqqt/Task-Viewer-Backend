package com.taskviewer.api.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtToken {

  private String token;

  public enum TokenType {
    ACCESS, REFRESH
  }
}
