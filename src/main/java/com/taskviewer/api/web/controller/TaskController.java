package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.Status;
import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TimeEstimate;
import com.taskviewer.api.model.User;
import com.taskviewer.api.postgres.PgTask;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rq.RqTask;
import com.taskviewer.api.web.rq.RqTaskSearchCriteria;
import com.taskviewer.api.web.rq.RqTaskUpdate;
import com.taskviewer.api.web.rq.RqTrackTime;
import com.taskviewer.api.web.rs.RsTask;
import com.taskviewer.api.web.security.jwt.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService tasks;
  private final UserService users;
  private final MailService mails;

  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void create(
    @AuthenticationPrincipal final JwtUserDetails principal,
    @RequestBody @Validated final RqTask request) {
    this.tasks.add(
      PgTask.builder()
        .title(request.title())
        .username(request.username())
        .about(request.about())
        .reporter(principal.getUsername())
        .status(new Status.Simple(request.status(), request.priority()))
        .time(new TimeEstimate.InMinutes(request.due(), 0))
        .build()
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/replicate/{id}")
  public void replicate(@PathVariable final Long id) {
    final Task task = this.tasks.byId(id);
    this.tasks.add(
      PgTask.builder()
        .title(task.title())
        .username(task.username())
        .status(task.status())
        .time(
          new TimeEstimate.InMinutes(
            task.time().due().plusDays(1L),
            0
          )
        )
        .build()
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{id}")
  public RsTask update(
    @PathVariable final Long id,
    @RequestBody @Validated final RqTaskUpdate request) {
    return new RsTask(
      this.tasks.update(id, request)
    );
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PatchMapping("/{id}")
  public RsTask track(@PathVariable final Long id,
                      @RequestBody @Validated final RqTrackTime request) {
    return new RsTask(this.tasks.track(id, request.minutes()));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable final Long id) {
    this.tasks.delete(id);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping
  public List<RsTask> byCriteria(
    @AuthenticationPrincipal JwtUserDetails principal,
    @RequestParam(required = false) final String title,
    @RequestParam(required = false) final String status,
    @RequestParam(required = false) final String priority,
    @RequestParam(required = false,
      defaultValue = "off") final String estimate,
    @RequestParam(required = false) final String sort
  ) {
    return this.tasks.byCriteria(
        new RqTaskSearchCriteria(
          title,
          principal.getUsername(),
          status,
          priority,
          estimate,
          sort
        )
      )
      .stream()
      .map(RsTask::new)
      .toList();
  }

  @PreAuthorize("@securityRulesHandler.canAccessTask(#id)")
  @PatchMapping("/close/{id}")
  public RsTask close(@PathVariable final Long id) {
    final RsTask done = new RsTask(
      this.tasks.update(id, "done")
    );
    this.mails.send(
      done.getReporter(), "Task %s is done by %s"
        .formatted(
          done.getTitle(),
          done.getUsername()
        ),
      "Task %s is done by %s"
        .formatted(
          done.getTitle(),
          done.getUsername()
        )
    );
    return done;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("assign/{id}/{username}")
  public RsTask assign(@PathVariable final Long id,
                       @PathVariable final String username) {
    final User user = this.users.byUsername(username);
    final Task assigned = this.tasks.assign(id, user.id());
    this.mails.send(
      user.email(),
      "Task assigned to you",
      "Task %s was assigned to you"
        .formatted(
          assigned.title()
        )
    );
    return new RsTask(assigned);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping("/my")
  public List<RsTask> assigned(
    @AuthenticationPrincipal final JwtUserDetails principal
  ) {
    return this.tasks.byUsername(principal.getUsername())
      .stream()
      .map(RsTask::new)
      .toList();
  }

}
