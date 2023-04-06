package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Comment;
import com.taskviewer.api.model.CommentNotFoundException;
import com.taskviewer.api.model.Comments;
import com.taskviewer.api.web.rq.RqComment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PgComments implements Comments {

  private final JdbcTemplate jdbc;

  @Override
  public void add(final RqComment request) {
    this.jdbc.update(
      """
        INSERT INTO comment(login, task, content)
        VALUES (
                (SELECT l.id FROM login l WHERE l.username = ?),
                ?,
                ?
        );
        """,
      request.username(),
      request.task(),
      request.content()
    );
  }

  @Override
  public Comment comment(final Long id) {
    return this.jdbc.query(
        """
          SELECT l.username AS username,
                 t.title    AS title,
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
      .findFirst()
      .orElseThrow(
        () ->
          new CommentNotFoundException(
            "Comment with id %s not found"
              .formatted(
                id
              )
          )
      );
  }

  @Override
  public Iterable<Comment> byUser(final Long user) {
    return this.jdbc.query(
      """
        SELECT l.username AS username,
                t.title    AS title,
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
  public Iterable<Comment> byTask(final Long task) {
    return this.jdbc.query(
      """
        SELECT l.username AS username,
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
  public Iterable<Comment> iterate(final Long user, final Long task) {
    return this.jdbc.query(
      """
         SELECT l.username AS username,
                 t.title    AS title,
                 c.content  AS content
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
