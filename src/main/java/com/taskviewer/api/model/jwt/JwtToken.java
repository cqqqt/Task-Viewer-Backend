package com.taskviewer.api.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

  private String token;

  public enum TokenType {
    ACCESS, REFRESH
  }
}
