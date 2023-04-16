package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
       		   t.due AS due,
       		   t.tracked AS tracked,
       		   t.created as task_created,   
       		   l.username as username,
       		   r.email AS reporter
    FROM task t LEFT JOIN login l on l.id = t.assigne
    LEFT JOIN login r ON r.id = t.reporter
    WHERE t.id = ?""";
  protected static final String FIND_BY_USERNAME = """
    SELECT t.id AS task_id,
    	   t.title AS title,
    	   t.about AS about,
    	   t.status AS status,
    	   t.priority AS priority,
    	   t.due AS due,
    	   t.tracked AS tracked,
    	   t.created as task_created,
    	   l.username as username,
    	   r.email AS reporter
    FROM task t INNER JOIN login l on l.id = t.assigne
    LEFT JOIN login r ON r.id = t.reporter
    WHERE l.username = ?""";
  protected static final String FIND_BY_EMAIL = """
    SELECT t.id AS task_id,
    	   t.title AS title,
    	   t.about AS about,
    	   t.status AS status,
    	   t.priority AS priority,
    	   t.due AS due,
    	   t.tracked AS tracked,
    	   t.created as task_created,
    	   l.username as username,
    	   r.email AS reporter
    FROM task t INNER JOIN login l on l.id = t.assigne
    LEFT JOIN login r ON r.id = t.reporter
    WHERE l.email = ?""";
  protected static final String FIND_WITH_PRIORITY = """
    SELECT t.id AS task_id,
    	   t.title AS title,
    	   t.about AS about,
    	   t.status AS status,
    	   t.priority AS priority,
    	   t.due AS due,
    	   t.tracked AS tracked,
    	   t.created as task_created,
    	   l.username as username,
    	   r.email AS reporter
    FROM task t LEFT JOIN login l on l.id = t.assigne
    LEFT JOIN login r ON r.id = t.reporter
    WHERE t.priority = ?""";
  protected static final String FIND_WITH_STATUS = """
    SELECT t.id AS task_id,
    	   t.title AS title,
    	   t.about AS about,
    	   t.status AS status,
    	   t.priority AS priority,
    	   t.due AS due,
    	   t.tracked AS tracked,
    	   t.created as task_created,	   
    	   l.username as username,
    	   r.email AS reporter
    FROM task t LEFT JOIN login l on l.id = t.assigne
    LEFT JOIN login r ON r.id = t.reporter
    WHERE t.status = ?""";
  protected static final String FIND_ALL = """
    SELECT t.id       AS task_id,
           t.title    AS title,
           t.about    AS about,
           t.status   AS status,
           t.priority AS priority,
           t.due      AS due,
           t.tracked  AS tracked,
           t.created  AS task_created,
           l.username AS username,
           r.email AS reporter
    FROM task t
             LEFT JOIN login l ON l.id = t.assigne
             LEFT JOIN login r ON r.id = t.reporter""";
  protected static final String CREATE = """
    INSERT INTO task(title, about, assigne, reporter, status, priority, due, tracked)
    VALUES (?, ?, (SELECT l.id FROM login l WHERE l.username = ?),
            (SELECT r.id FROM login r WHERE r.username = ?),
            ?, ?, ?, ?);""";
  protected static final String UPDATE = """
    UPDATE task set title = ?,
    							  about = ?,
    							  assigne = (SELECT l.id FROM login l WHERE l.username = ?),
    							  status = ?,
    							  priority = ?,
    							  due = ?,
    							  tracked = ?
    WHERE task.id = ?""";
  protected static final String ASSIGN = """
    UPDATE task SET assigne = ?
    WHERE task.id = ?""";

  @Override
  public Optional<Task> byId(Long id) {
    return jdbc.query(FIND_BY_ID, view, id)
      .stream()
      .findFirst();
  }

  @Override
  public List<Task> weeklyByUser(final Long user) {
    return this.jdbc.query(
      """
        SELECT t.id       AS task_id,
               t.title    AS title,
               t.about    AS about,
               t.status   AS status,
               t.priority AS priority,
               t.due AS due,
               t.tracked  AS tracked,
               t.created  as task_created,
               l.username as username,
               r.email AS reporter
        FROM task t
                 JOIN login l on l.id = t.assigne
                 LEFT JOIN login r ON r.id = t.reporter
        WHERE t.status != 'done'
          AND l.id = ?
          AND due BETWEEN
              CURRENT_TIMESTAMP AND
              CURRENT_TIMESTAMP + INTERVAL '7 days'
        				""",
      this.view,
      user
    );
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
  public List<Task> byCriteria(String sql) {
    return jdbc.query(sql, view);
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
  public List<Task> openAndAssigned() {
    return this.jdbc.query(
      """
        SELECT t.id       AS task_id,
                 t.title    AS title,
                 t.about    AS about,
                 t.status   AS status,
                 t.priority AS priority,
                 t.due AS due,
                 t.tracked  AS tracked,
                 t.created  as task_created,
                 l.username as username,
                 r.email AS reporter
          FROM task t
                   JOIN login l on l.id = t.assigne
                   LEFT JOIN login r ON r.id = t.reporter
          WHERE t.status != 'done'
          			""",
      this.view
    );
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
      task.reporter(),
      task.status().value(),
      task.status().priority(),
      task.time().due(),
      task.time().tracked());
  }

  @Override
  public void update(String sql) {
    jdbc.update(sql);
  }

  @Override
  public void update(final Long id, final Integer minutes) {
    this.jdbc.update(
      """
        UPDATE task
        SET tracked = tracked + ?
        WHERE id = ?
        """,
      minutes,
      id
    );
  }

  @Override
  public void update(final Long id, final String status) {
    this.jdbc.update(
      """
        UPDATE task 
        SET status = ?
        WHERE id = ?;
        """,
      status,
      id
    );
  }

  @Override
  public void assign(final Long id, final Long user) {
    this.jdbc.update(ASSIGN, user, id);
  }

  @Override
  public void delete(final Long id) {
    this.jdbc.update(
      """
        DELETE FROM task WHERE id = ?
        """,
      id
    );
  }

}
