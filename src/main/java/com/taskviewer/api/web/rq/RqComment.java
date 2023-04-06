package com.taskviewer.api.web.rq;

public record RqComment(
  String content,
  String username,
  String title
) {
}
