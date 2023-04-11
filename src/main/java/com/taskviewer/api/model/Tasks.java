package com.taskviewer.api.model;

import java.util.List;
import java.util.Optional;

public interface Tasks {

  Optional<Task> byId(Long id);

  List<Task> byUsername(String username);

  List<Task> byEmail(String email);

  List<Task> byCriteria(String sql);

  List<Task> withPriority(int priority);

  List<Task> withStatus(String status);

  List<Task> all();

  void add(Task task);

  void update(Task task);

	void assign(Long id, Long user);

}
