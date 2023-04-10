package com.taskviewer.api.postgres;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BooleanRowMapper implements RowMapper<Boolean> {

  @Override
  public Boolean mapRow(final ResultSet rs, final int rows) throws SQLException {
    return rs.getBoolean("exists");
  }
}
