package com.taskviewer.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {
  }

  public UserAlreadyExistsException(final String message) {
    super(message);
  }

  public UserAlreadyExistsException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExistsException(final Throwable cause) {
    super(cause);
  }
}
