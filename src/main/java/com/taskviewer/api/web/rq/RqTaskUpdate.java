package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record RqTaskUpdate(
  @Size(max = 128, message = "Title can not be more than 128 characters")
  String title,
  @Size(max = 512, message = "Content can not be more than 512 characters")
  String about,
  @Size(max = 32, message = "Username can not be more than 32 characters")
  String username,
  @Size(max = 32, message = "Status can not be more than 32 characters")
  String status,
  Integer priority,
  LocalDateTime due,
  Integer tracked
) {

  @Contract(value = " -> new", pure = true)
  public static @NotNull RqTaskUpdate.TaskUpdateSqlBuilder taskUpdateSqlBuilder() {
    return new RqTaskUpdate.TaskUpdateSqlBuilder();
  }

  public static class TaskUpdateSqlBuilder {
    private final StringBuilder sql = new StringBuilder("UPDATE task set updated = now()");

    public TaskUpdateSqlBuilder withTitle(String title) {
      if (title != null) {
        sql.append(", title = '")
          .append(title)
          .append("'");
      }
      return this;
    }

    public TaskUpdateSqlBuilder withAbout(String about) {
      if (about != null) {
        sql.append(", about = '")
          .append(about)
          .append("'");
      }
      return this;
    }

    public TaskUpdateSqlBuilder withAssignee(String username) {
      if (username != null) {
        sql.append(", assigne = (select l.id from login l where l.username = '")
          .append(username)
          .append("')");
      }
      return this;
    }

    public TaskUpdateSqlBuilder withStatus(String status) {
      if (status != null) {
        sql.append(", status = '")
          .append(status)
          .append("'");
      }
      return this;
    }

    public TaskUpdateSqlBuilder withPriority(Integer priority) {
      if (priority != null) {
        sql.append(", priority = ")
          .append(priority);
      }
      return this;
    }

    public TaskUpdateSqlBuilder withDue(LocalDateTime due) {
      if (due != null) {
        sql.append(", due = ")
          .append(Timestamp.valueOf(due));
      }
      return this;
    }

    public TaskUpdateSqlBuilder withTracked(Integer tracked) {
      if (tracked != null) {
        sql.append(", tracked = ")
          .append(tracked);
      }
      return this;
    }

    public String build(Long id) {
      sql.append(" where task.id = ")
        .append(id);
      return sql.toString();

    }
  }
}