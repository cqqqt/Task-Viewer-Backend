package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RqComment(
  @NotNull
  @Size(max = 512, message = "Content can not be more than 512 characters")
  String content,
  @Deprecated
  String username,
  @NotNull
  Long task
) {
}
