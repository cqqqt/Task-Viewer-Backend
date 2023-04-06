package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PgComment implements Comment {

  private Long id;
  private String content;
  private String username;
  private String task;

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
  public String task() {
    return this.task;
  }
}
