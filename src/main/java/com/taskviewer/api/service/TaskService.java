package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;

import java.util.List;

public interface TaskService {

	Task byId(Long id);

	List<Task> byUsername(String username);

	List<Task> byEmail(String email);

	List<Task> withPriority(int priority);

	List<Task> withStatus(String status);

	List<Task> all();

	Task add(Task task);

	Task update(Task task);

	Task assign(Long id, Long user);

}
