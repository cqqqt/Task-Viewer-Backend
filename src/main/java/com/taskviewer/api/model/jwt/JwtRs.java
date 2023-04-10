package com.taskviewer.api.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtRs {

  private JwtToken access;
  private JwtToken refresh;
}
