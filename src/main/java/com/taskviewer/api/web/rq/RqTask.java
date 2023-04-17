package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record RqTask(
  @NotNull
  @Size(max = 32, message = "Username can not be more than 32 characters")
  String username,
  @NotNull
  @Size(max = 128, message = "Title can not be more than 128 characters")
  String title,
  @Size(max = 512, message = "Content can not be more than 512 characters")
  String about,
  @NotNull
  @Size(max = 32, message = "Status can not be more than 32 characters")
  String status,
  int priority,
  LocalDateTime due
) {
}
