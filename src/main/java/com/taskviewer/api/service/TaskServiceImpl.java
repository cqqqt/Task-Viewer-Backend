package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TaskNotFoundException;
import com.taskviewer.api.model.Tasks;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final Tasks tasks;

	@Override
	@Transactional(readOnly = true)
	public Task byId(Long id) {
		return tasks.byId(id).orElseThrow(TaskNotFoundException::new);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> byUsername(String username) {
		return tasks.byUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> byEmail(String email) {
		return tasks.byEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> withPriority(int priority) {
		return tasks.withPriority(priority);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> withStatus(String status) {
		return tasks.withStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> all() {
		return tasks.all();
	}

	@Override
	@Transactional
	public Task add(Task task) {
		this.tasks.add(task);
		return this.byId( task.id() );
	}

	@Override
	@Transactional
	public Task update(@NotNull Task task) {
		this.tasks.update(task);
		return this.byId( task.id() );
	}

}
