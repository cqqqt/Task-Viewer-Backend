package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.Size;

public record RqUserUpdate(
  @Size(max = 32, message = "Firstname can not be more than 32 characters")
  String firstname,
  @Size(max = 32, message = "Lastname can not be more than 32 characters")
  String lastname
) {
}
