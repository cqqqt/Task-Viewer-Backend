package com.taskviewer.api.postgres;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.model.Users;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PgUsers implements Users {

  private final JdbcTemplate jdbc;

  @Override
  public void add(final RqUser user) {
    this.jdbc.update(
      """
        INSERT INTO login(
        username, firstname, lastname, password, role, email
        )
        VALUES (?, ?, ?, ?, ?, ?); 
        """,
      user.username(),
      user.firstname(),
      user.lastname(),
      user.password(),
      user.role(),
      user.email()
    );
  }

  @Override
  public void update(final Long id, final RqUserUpdate request) {
    this.jdbc.update(
      """
        UPDATE login 
        SET firstname = ?,
            lastname = ?
        WHERE id = ?;
        """,
      request.firstname(),
      request.lastname(),
      id
    );
  }

  @Override
  public User user(final Long id) {
    return this.jdbc.query(
        """
          SELECT l.id AS id,
                 l.username AS username,
                 l.email AS email,
                 l.firstname AS firstname,
                 l.lastname AS lastname,
                 l.role AS role
                 FROM login l
          WHERE l.id = ?
                    """,
        new SafeUser(),
        id
      )
      .stream()
      .findFirst()
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with id %s is not found"
              .formatted(
                id
              )
          )
      );
  }

  @Override
  public User user(final String username) {
    return this.jdbc.query(
        """
          SELECT l.id AS id,
                 l.username AS username,
                 l.email AS email,
                 l.firstname AS firstname,
                 l.lastname AS lastname,
                 l.role AS role
                 FROM login l
          WHERE l.username = ? 
          """,
        new SafeUser(),
        username
      )
      .stream()
      .findFirst()
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with username %s is not found"
              .formatted(
                username
              )
          )
      );
  }

  @Override
  public User byEmail(final String email) {
    return this.jdbc.query(
        """
          SELECT l.id AS id,
                 l.username AS username,
                 l.email AS email,
                 l.firstname AS firstname,
                 l.lastname AS lastname,
                 l.role AS role
                 FROM login l
          WHERE l.email = ?
          """,
        new SafeUser(),
        email
      )
      .stream()
      .findFirst()
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with email %s is not found"
              .formatted(
                email
              )
          )
      );
  }

  @Override
  public List<User> iterate(final String firstname, final String lastname) {
    return this.jdbc.query(
      """
        SELECT l.id AS id,
                 l.username AS username,
                 l.email AS email,
                 l.firstname AS firstname,
                 l.lastname AS lastname,
                 l.role AS role
                 FROM login l
          WHERE l.firstname = ? 
          AND l.lastname = ?       
        """,
      new SafeUser(),
      firstname,
      lastname
    );
  }

  @Override
  public List<User> iterate() {
    return this.jdbc.query(
      """
        SELECT l.id AS id,
                 l.username AS username,
                 l.email AS email,
                 l.firstname AS firstname,
                 l.lastname AS lastname,
                 l.role AS role
                 FROM login l
        """,
      new SafeUser()
    );
  }
}