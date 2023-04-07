package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TaskNotFoundException;
import com.taskviewer.api.model.Tasks;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PgTasks implements Tasks {

	private final JdbcTemplate jdbc;
	private final ViewTask view;

	protected static final String FIND_BY_ID = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t
		WHERE t.id = ?""";
	protected static final String FIND_BY_USERNAME = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE l.username = ?""";
	protected static final String FIND_BY_EMAIL = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE l.email = ?""";
	protected static final String FIND_WITH_PRIORITY = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t
		WHERE t.priority = ?""";
	protected static final String FIND_WITH_STATUS = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t
		WHERE t.status = ?""";
	protected static final String FIND_ALL = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked
		FROM task t""";

	@Override
	public Task task(Long id) {
		return jdbc.query(FIND_BY_ID, view, id)
				.stream()
				.findFirst()
				.orElseThrow(() ->
						new TaskNotFoundException("Task with id %s not found".formatted(id))
				);
	}

	@Override
	public Iterable<Task> iterate(String username) {
		return jdbc.query(FIND_BY_USERNAME, view, username);
	}

	@Override
	public Iterable<Task> byEmail(String email) {
		return jdbc.query(FIND_BY_EMAIL, view, email);
	}

	@Override
	public Iterable<Task> iterate(int priority) {
		return jdbc.query(FIND_WITH_PRIORITY, view, priority);
	}

	@Override
	public Iterable<Task> with(String status) {
		return jdbc.query(FIND_WITH_STATUS, view, status);
	}

	@Override
	public Iterable<Task> iterate() {
		return jdbc.query(FIND_ALL, view);
	}

}
