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

  Task add(Task task);

  Task update(Task task);

  Task assign(Long id, Long user);

}
