package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Comment;
import com.taskviewer.api.model.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PgComments implements Comments {

  private final JdbcTemplate jdbc;
  private final NamedParameterJdbcTemplate named;

  @Override
  public Long add(final Comment comment) {
    final KeyHolder keys = new GeneratedKeyHolder();
    final Map<String, Object> values = new HashMap<>(4);
    values.put("login", comment.username());
    values.put("task", comment.task());
    values.put("content", comment.content());
    this.named.update(
      """
        INSERT INTO comment(login, task, content)
        VALUES (
                (SELECT l.id FROM login l WHERE l.username = :login),
                :task,
                :content
        );
        """,
      new MapSqlParameterSource(
        values
      ),
      keys,
      new String[] {"id"}
    );
    return (Long) keys.getKey();
  }

  @Override
  public void delete(final Long id) {
    this.jdbc.update(
      """
        DELETE FROM comment WHERE id = ?
        """,
      id
    );
  }

  @Override
  public void update(final Comment comment) {
    this.jdbc.update(
      """
        UPDATE comment
        SET content = ?
        WHERE id = ?
        """,
      comment.content(),
      comment.id()
    );
  }

  @Override
  public Optional<Comment> byId(final Long id) {
    return this.jdbc.query(
        """
          SELECT c.id AS id,
              l.username AS username,
                 t.id    AS tid,
                 c.content  AS content
          FROM comment c
                   JOIN login l on l.id = c.login
                   JOIN task t on t.id = c.task
          WHERE c.id = ?                    
          """,
        new ViewComment(),
        id
      )
      .stream()
      .findFirst();
  }

  @Override
  public List<Comment> byUser(final Long user) {
    return this.jdbc.query(
      """
        SELECT c.id as id,
               l.username AS username,
               t.id    AS tid,
               c.content  AS content
        FROM comment c
                  JOIN login l on l.id = c.login
                  JOIN task t on t.id = c.task
        WHERE l.id = ?;
        """,
      new ViewComment(),
      user
    );
  }

  @Override
  public List<Comment> byTask(final Long task) {
    return this.jdbc.query(
      """
        SELECT c.id AS id,
                l.username AS username,
                t.id       AS tid,
                t.title    AS title,
                c.content  AS content
        FROM comment c
                  JOIN login l on l.id = c.login
                  JOIN task t on t.id = c.task
        WHERE t.id = ?;
        """,
      new ViewComment(),
      task
    );
  }

  @Override
  public List<Comment> iterate(final Long user, final Long task) {
    return this.jdbc.query(
      """
         SELECT l.username AS username,
                 t.title    AS title,
                 c.content  AS content,
                 t.id AS tid,
                 c.id AS id
         FROM comment c
                   JOIN login l on l.id = c.login
                   JOIN task t on t.id = c.task
         WHERE l.id = ? AND t.id = ?;
        """,
      new ViewComment(),
      user,
      task
    );
  }

}
