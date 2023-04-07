package com.taskviewer.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CriteriaIsNotSpecifiedException extends RuntimeException {

  public CriteriaIsNotSpecifiedException() {
  }

  public CriteriaIsNotSpecifiedException(final String message) {
    super(message);
  }

  public CriteriaIsNotSpecifiedException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CriteriaIsNotSpecifiedException(final Throwable cause) {
    super(cause);
  }
}
