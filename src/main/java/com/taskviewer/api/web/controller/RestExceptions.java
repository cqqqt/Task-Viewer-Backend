package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.AuthenticationException;
import com.taskviewer.api.model.UserAlreadyExistsException;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.web.rs.RsError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptions {

  @ExceptionHandler(UserNotFoundException.class)
  public RsError userNotFound(final UserNotFoundException ex) throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new RsError.WithCode(
      404,
      message
    );
  }

  @ExceptionHandler(AuthenticationException.class)
  public RsError authenticationException(final AuthenticationException ex) {
    final String message = ex.getMessage();
    log.debug(message);
    return new RsError.WithCode(
      401,
      message
    );
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public RsError alreadyExistsException(final UserAlreadyExistsException ex) {
    final String message = ex.getMessage();
    log.debug(message);
    return new RsError.WithCode(
      409,
      message
    );
  }

  @ExceptionHandler(AccessDeniedException.class)
  public RsError accessDeniedException(final AccessDeniedException ex) {
    final String message = ex.getMessage();
    log.debug(message);
    return new RsError.WithCode(
      403,
      message
    );
  }
}