package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RqUser(
  @NotNull
  @Size(max = 32, message = "Username can not be more than 32 characters")
  String username,
  @Size(max = 32, message = "Firstname can not be more than 32 characters")
  String firstname,
  @Size(max = 32, message = "Lastname can not be more than 32 characters")
  String lastname,
  @NotNull
  @Size(max = 512, message = "Password can not be more than 512 characters")
  String password,
  String role,
  @Email
  @NotNull
  @Size(max = 256, message = "Email can not be more than 256 characters")
  String email
) {
}