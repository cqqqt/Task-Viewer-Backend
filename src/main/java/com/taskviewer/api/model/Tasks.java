package com.taskviewer.api.model;

import java.util.Optional;

public interface Tasks {

  Optional<Task> byId(Long id);

  Iterable<Task> byUsername(String username);

  Iterable<Task> byEmail(String email);

  Iterable<Task> withPriority(int priority);

  Iterable<Task> withStatus(String status);

  Iterable<Task> all();

  void add(Task task);

  void update(Task task);

  void assign(Long id, Long user);

}
