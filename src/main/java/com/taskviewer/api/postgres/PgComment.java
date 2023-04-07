package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PgComment implements Comment {

  private Long id;
  private String content;
  private String username;
  private Long task;

  @Override
  public Comment withId(final Long id) {
    return new PgComment(
      id,
      this.content,
      this.username,
      this.task
    );
  }

  @Override
  public Long id() {
    return this.id;
  }

  @Override
  public String content() {
    return this.content;
  }

  @Override
  public String username() {
    return this.username;
  }

  @Override
  public Long task() {
    return this.task;
  }
}
