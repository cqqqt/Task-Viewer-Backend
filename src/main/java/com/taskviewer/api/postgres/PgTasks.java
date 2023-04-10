package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE t.id = ?""";
	protected static final String FIND_BY_USERNAME = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE l.username = ?""";
	protected static final String FIND_BY_EMAIL = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE l.email = ?""";
	protected static final String FIND_WITH_PRIORITY = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE t.priority = ?""";
	protected static final String FIND_WITH_STATUS = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne
		WHERE t.status = ?""";
	protected static final String FIND_ALL = """
		SELECT t.id AS task_id,
			   t.title AS title,
			   t.about AS about,
			   t.status AS status,
			   t.priority AS priority,
			   t.estimate AS estimate,
			   t.tracked AS tracked,
			   
			   l.username as username
		FROM task t INNER JOIN login l on l.id = t.assigne""";
	protected static final String CREATE = """
        INSERT INTO task(title, about, assigne, status, priority, estimate, tracked)
        VALUES (?, ?, (SELECT l.id FROM login l WHERE l.username = ?), ?, ?, ?, ?)""";
	protected static final String UPDATE = """
        UPDATE task set title = ?,
                        about = ?,
                        assigne = (SELECT l.id FROM login l WHERE l.username = ?),
                        status = ?,
                        priority = ?,
                        estimate = ?,
                        tracked = ?
        WHERE task.id = ?""";
	protected static final String ASSIGN = """
			UPDATE task assigne = (SELECT l.id FROM login l WHERE l.username = ?)
			WHERE task.id = ?""";

	@Override
	public Optional<Task> byId(Long id) {
		return jdbc.query(FIND_BY_ID, view, id)
				.stream()
				.findFirst();
	}

	@Override
	public List<Task> byUsername(String username) {
		return jdbc.query(FIND_BY_USERNAME, view, username);
	}

	@Override
	public List<Task> byEmail(String email) {
		return jdbc.query(FIND_BY_EMAIL, view, email);
	}

	@Override
	public List<Task> withPriority(int priority) {
		return jdbc.query(FIND_WITH_PRIORITY, view, priority);
	}

	@Override
	public List<Task> withStatus(String status) {
		return jdbc.query(FIND_WITH_STATUS, view, status);
	}

	@Override
	public List<Task> all() {
		return jdbc.query(FIND_ALL, view);
	}

	@Override
	public void add(@NotNull Task task) {
		jdbc.update(CREATE,
				task.title(),
				task.about(),
				task.username(),
				task.status().value(),
				task.status().priority(),
				task.time().estimate(),
				task.time().tracked());
	}

	@Override
	public void update(@NotNull Task task) {
		jdbc.update(UPDATE,
				task.title(),
				task.about(),
				task.username(),
				task.status().value(),
				task.status().priority(),
				task.time().estimate(),
				task.time().tracked());
	}

	@Override
	public void assign(Long id, Long user) {
		jdbc.update(ASSIGN, user, id);
	}

}
