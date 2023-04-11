package com.taskviewer.api.postgres;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.Users;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PgUsers implements Users {

  private final JdbcTemplate jdbc;

  @Override
  public void add(final @NotNull User user) {
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
      user.role().name(),
      user.email()
    );
  }

  @Override
  public void update(final @NotNull User user) {
    this.jdbc.update(
      """
        UPDATE login 
        SET firstname = ?,
            lastname = ?
        WHERE id = ?;
        """,
      user.firstname(),
      user.lastname(),
      user.id()
    );
  }

  @Override
  public Optional<User> byId(final Long id) {
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
      .findFirst();
  }

  @Override
  public Optional<User> byUsername(final String username) {
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
      .findFirst();
  }

  @Override
  public Optional<User> byEmail(final String email) {
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
      .findFirst();
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

  @Override
  public Optional<String> password(Long id) {
    return this.jdbc.query(
        """
          SELECT l.password AS password
          FROM login l
          WHERE l.id = ?
          """,
        new PasswordRowMapper(),
        id
      )
      .stream()
      .findFirst();
  }

  @Override
  public boolean exists(String email, String username) {
    return this.jdbc.query(
        """
          SELECT EXISTS(
          SELECT 1
          FROM login
          WHERE email = ?
          OR username = ?
          )
          """,
        new ExistsRowMapper(),
        email,
        username
      )
      .stream()
      .findFirst()
      .orElse(false);
  }
}