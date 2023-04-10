package com.taskviewer.api.model;

import java.time.LocalDateTime;

public interface Task {

  Long id();

  String username();

  String title();

  String about();

  Status status();

  TimeEstimate time();

  LocalDateTime created();
}