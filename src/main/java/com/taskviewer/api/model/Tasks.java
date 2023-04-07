package com.taskviewer.api.model;

public interface Tasks {

  Task byId(Long id);

  Iterable<Task> byUsername(String username);

  Iterable<Task> byEmail(String email);

  Iterable<Task> withPriority(int priority);

  Iterable<Task> withStatus(String status);

  Iterable<Task> all();

}
