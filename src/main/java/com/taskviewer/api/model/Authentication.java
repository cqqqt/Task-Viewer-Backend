package com.taskviewer.api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authentication {

  @NotNull
  @Size(max = 32, message = "Username can not be more than 32 characters")
  private String username;
  @NotNull
  @Size(max = 512, message = "Password can not be more than 512 characters")
  private String password;
}
