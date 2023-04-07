package com.taskviewer.api.web.rs;

import com.taskviewer.api.model.Comment;
import lombok.Data;

@Data
public class RsComment {

  private Long id;
  private Long task;
  private String username;
  private String content;

  public RsComment(final Comment comment) {
    this(
      comment.id(),
      comment.task(),
      comment.username(),
      comment.content()
    );
  }

  public RsComment(final Long id,
                   final Long tsk,
                   final String user,
                   final String cntnt) {
    this.id = id;
    this.task = tsk;
    this.username = user;
    this.content = cntnt;
  }
}
