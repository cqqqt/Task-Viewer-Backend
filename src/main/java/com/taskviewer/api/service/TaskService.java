package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.web.rq.RqTaskSearchCriteria;
import com.taskviewer.api.web.rq.RqTaskUpdate;

import java.util.List;

public interface TaskService {

  Task byId(Long id);

  List<Task> byUsername(String username);

  List<Task> byEmail(String email);

  List<Task> byCriteria(RqTaskSearchCriteria criteria);

  List<Task> withPriority(int priority);

  List<Task> withStatus(String status);

  List<Task> openAndAssigned();

  List<Task> all();

  void add(Task task);

  Task update(Long id, RqTaskUpdate request);

  Task update(Long id, String status);

  Task assign(Long id, Long user);

  void delete(Long id);

  Task track(Long id, Integer minutes);
}
