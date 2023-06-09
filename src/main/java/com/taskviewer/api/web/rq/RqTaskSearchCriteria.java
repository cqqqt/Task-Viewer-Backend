package com.taskviewer.api.web.rq;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record RqTaskSearchCriteria(
  String title,
  String username,
  String status,
  String priority,
  String estimate,
  String sort) {

  @Contract(value = " -> new", pure = true)
  public static @NotNull TaskSearchSqlBuilder taskSearchSqlBuilder() {
    return new TaskSearchSqlBuilder();
  }

  public static class TaskSearchSqlBuilder {
    private final StringBuilder sql = new StringBuilder("""
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
      WHERE true""");

    public TaskSearchSqlBuilder withTitle(String title) {
      if (title != null) {
        sql.append(" and title = '")
          .append(title)
          .append("'");
      }
      return this;
    }

    public TaskSearchSqlBuilder withUsername(String username) {
      if (username != null) {
        sql.append(" and l.username = '")
          .append(username)
          .append("'");
      }
      return this;
    }

    public TaskSearchSqlBuilder withStatus(String status) {
      if (status != null) {
        sql.append(" and status = '")
          .append(status)
          .append("'");
      }
      return this;
    }

    public TaskSearchSqlBuilder withPriority(Integer priority) {
      if (priority != null) {
        sql.append(" and priority = ")
          .append(priority);
      }
      return this;
    }

    public TaskSearchSqlBuilder withEstimate(LocalDateTime estimate) {
      if (estimate != null) {
        sql.append(" and due = ")
          .append(Timestamp.valueOf(estimate));
      }
      return this;
    }

    public TaskSearchSqlBuilder withSort(final String sort) {
      if (sort != null && this.matches(sort)) {
        this.sql.append(" ORDER BY ")
          .append(sort);
      }
      return this;
    }

    public boolean matches(final String sort) {
      return "status".equals(sort) || "title".equals(sort)
        || "username".equals(sort) || "priority".equals(sort);
    }

    public String build() {
      return sql.toString();
    }

  }

}
