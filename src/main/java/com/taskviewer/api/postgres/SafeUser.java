package com.taskviewer.api.postgres;

import com.taskviewer.api.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SafeUser implements RowMapper<User> {

  @Override
  public User mapRow(final ResultSet rs, final int rows) throws SQLException {
    return PgUser.builder()
      .id(rs.getLong("id"))
      .username("username")
      .email("email")
      .firstname("firstname")
      .lastname("lastname")
      .role("role")
      .build();
  }
}
