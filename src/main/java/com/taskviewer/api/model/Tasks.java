package com.taskviewer.api.model;

public interface Tasks {

  Task task(Long id);

  Iterable<Task> iterate(String username);

  Iterable<Task> byEmail(String email);

  Iterable<Task> iterate(int priority);

  Iterable<Task> with(String status);

  Iterable<Task> iterate();

}
