package com.taskviewer.api.web.rs;

import com.taskviewer.api.model.Task;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RsTask {

  private Long id;
  private String title;
  private String status;
  private String username;
  private Integer priority;
  private String about;
  private LocalDateTime estimate;
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
      task.time().estimate(),
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
                final LocalDateTime estimate,
                final LocalDateTime tracked,
                final LocalDateTime created) {
    this.id = id;
    this.title = title;
    this.status = status;
    this.username = username;
    this.priority = priority;
    this.about = about;
    this.estimate = estimate;
    this.tracked = tracked;
    this.created = created;
  }
}