package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;

public interface TaskService {

	Task byId(Long id);

	Iterable<Task> byUsername(String username);

	Iterable<Task> byEmail(String email);

	Iterable<Task> withPriority(int priority);

	Iterable<Task> withStatus(String status);

	Iterable<Task> all();

	Task add(Task task);

	Task update(Task task);

}
