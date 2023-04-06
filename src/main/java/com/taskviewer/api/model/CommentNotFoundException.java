package com.taskviewer.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException() {
    super();
  }

  public CommentNotFoundException(final String message) {
    super(message);
  }

  public CommentNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CommentNotFoundException(final Throwable cause) {
    super(cause);
  }
}
