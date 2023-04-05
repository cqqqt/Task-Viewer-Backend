package com.taskviewer.api.postgres;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.model.Users;
import lombok.RequiredArgsConstructor;
import org.cactoos.text.FormattedText;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PgUsers implements Users {

  private final JdbcTemplate jdbc;

  @Override
  public void add(final User user) {
    this.jdbc.execute(
      """
        INSERT INTO login(
        username, firstname, lastname, password, role, email
        )
        VALUES (?, ?, ?, ?, ?, ?, ?) 
        """
    );
  }

  @Override
  public User user(final Long id) throws UserNotFoundException {
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
      .orElseThrow(() ->
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
    return null;
  }

  @Override
  public User byEmail(String email) {
    throw new UnsupportedOperationException("#byEmail()");
  }

  @Override
  public Iterable<User> iterate(String firstname, String lastname) {
    throw new UnsupportedOperationException("#iterate()");
  }

  @Override
  public Iterable<User> iterate() {
    throw new UnsupportedOperationException("#iterate()");
  }
}