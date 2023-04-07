package com.taskviewer.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

	public TaskNotFoundException() {
		super();
	}

	public TaskNotFoundException(final String message) {
		super(message);
	}

	public TaskNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TaskNotFoundException(final Throwable cause) {
		super(cause);
	}

}
