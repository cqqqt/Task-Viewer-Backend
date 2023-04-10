package com.taskviewer.api.service;

public record CommentSearchCriteria(
  Long user,
  Long task
) {
}