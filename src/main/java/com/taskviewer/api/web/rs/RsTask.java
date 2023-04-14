package com.taskviewer.api.web.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taskviewer.api.model.Task;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsTask {

  private Long id;
  private String title;
  private String status;
  private String username;
  private Integer priority;
  private String about;
  private LocalDateTime due;
  private LocalDateTime tracked;
  private LocalDateTime created;

  public RsTask(final Task task) {
    this(
      task.id(),
      task.title(),
      task.status().value(),
      task.username(),
      task.status().priority(),
      task.about(),
      task.time().due(),
      task.time().tracked(),
      task.created()
    );
  }

  public RsTask(final Long id,
                final String title,
                final String status,
                final String username,
                final Integer priority,
                final String about,
                final LocalDateTime due,
                final LocalDateTime tracked,
                final LocalDateTime created) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.username = username;
    this.priority = priority;
    this.about = about;
    this.due = due;
    this.tracked = tracked;
    this.created = created;
  }
}
