package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.User;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rs.RsTask;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService tasks;
  private final UserService users;
  private final MailService mails;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("assign/{id}/{username}")
  public RsTask assign(@PathVariable final Long id,
                       @PathVariable final String username) {
    final User user = this.users.byUsername(username);
    final Task assigned = this.tasks.assign(id, user.id());
    this.mails.send(
      user,
      "Task assigned to you",
      "Task %s was assigned to you"
        .formatted(
          assigned.title()
        )
    );
    return new RsTask(assigned);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping("/@{username}")
  public List<RsTask> assigned(@PathVariable final String username) {
    return this.tasks.byUsername(username)
      .stream()
      .map(RsTask::new)
      .toList();
  }

}
