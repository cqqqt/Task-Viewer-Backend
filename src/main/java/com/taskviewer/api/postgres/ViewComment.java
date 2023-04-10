package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ViewComment implements RowMapper<Comment> {

  @Override
  public Comment mapRow(final ResultSet rs, final int rows) throws SQLException {
    return new PgComment(
      rs.getLong("id"),
      rs.getString("content"),
      rs.getString("username"),
      rs.getLong("tid")
    );
  }
}
