package com.taskviewer.api.web.rs;

import com.taskviewer.api.model.AuthenticationException;
import com.taskviewer.api.model.TaskNotFoundException;
import com.taskviewer.api.model.UserAlreadyExistsException;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.web.rs.JsonError;
import com.taskviewer.api.web.rs.RsError;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptions {

  @ExceptionHandler(UserNotFoundException.class)
  public RsError userNotFound(final UserNotFoundException ex)
    throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new JsonError(
      new RsError.WithCode(
        404,
        message
      ).content()
    );
  }

  @ExceptionHandler(TaskNotFoundException.class)
  public RsError taskNotFound(final TaskNotFoundException ex)
    throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new JsonError(
      new RsError.WithCode(
        404,
        message
      ).content()
    );
  }

  @ExceptionHandler(AuthenticationException.class)
  public RsError authenticationException(final AuthenticationException ex)
    throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new JsonError(
      new RsError.WithCode(
        401,
        message
      ).content()
    );
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public RsError alreadyExistsException(final UserAlreadyExistsException ex)
    throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new JsonError(
      new RsError.WithCode(
        409,
        message
      ).content()
    );
  }

  @ExceptionHandler(AccessDeniedException.class)
  public RsError accessDeniedException(final AccessDeniedException ex)
    throws Exception {
    final String message = ex.getMessage();
    log.debug(message);
    return new JsonError(
      new RsError.WithCode(
        403,
        message
      ).content()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RsError handle(final MethodArgumentNotValidException ex) throws Exception {
    final Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(fieldError ->
      errors.put(fieldError.getField(), fieldError.getDefaultMessage())
    );
    log.debug(ex.getMessage());
    return new JsonError(
      new RsError.WithCode(
        400,
        errors.values().toString()
      ).content()
    );
  }
}