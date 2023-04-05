package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.User;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class PgUser implements User {

  private Long id;
  private String username;
  private String email;
  private String role;
  private String firstname;
  private String lastname;
  private String password;
  private LocalDateTime created;
  private List<Task> tasks;

  @Override
  public Long id() {
    return this.id;
  }

  @Override
  public String username() {
    return this.username;
  }

  @Override
  public String role() {
    return this.role;
  }

  @Override
  public String email() {
    return this.email;
  }

  @Override
  public Iterable<Task> tasks() {
    return Collections.unmodifiableList(this.tasks);
  }
}
