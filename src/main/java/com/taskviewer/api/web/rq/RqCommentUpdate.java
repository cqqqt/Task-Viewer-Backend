package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RqCommentUpdate(
  @NotNull
  @Size(max = 516, message = "Content can not be more than 516 characters")
  String content) {
}
