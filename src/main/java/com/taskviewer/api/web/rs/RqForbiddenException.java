package com.taskviewer.api.web.rs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RqForbiddenException extends RuntimeException {

  public RqForbiddenException() {
  }

  public RqForbiddenException(final String message) {
    super(message);
  }

  public RqForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public RqForbiddenException(final Throwable cause) {
    super(cause);
  }
}
