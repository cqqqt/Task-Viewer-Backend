package com.taskviewer.api.service;

import com.taskviewer.api.model.Comments;
import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TaskNotFoundException;
import com.taskviewer.api.model.Tasks;
import com.taskviewer.api.web.rq.RqTaskSearchCriteria;
import com.taskviewer.api.web.rq.RqTaskUpdate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final Tasks tasks;
  private final Comments comments;

  @Override
  @Transactional(readOnly = true)
  public Task byId(final Long id) {
    log.debug("find task by id {}", id);
    return this.tasks.byId(id).orElseThrow(TaskNotFoundException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> byUsername(final String username) {
    log.debug("find tasks by assignee's username {}", username);
    return this.tasks.byUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> byEmail(final String email) {
    log.debug("find tasks by assignee's email {}", email);
    return this.tasks.byEmail(email);
  }

  @Override
  @Transactional
  public List<Task> byCriteria(final RqTaskSearchCriteria criteria) {
    log.debug("task search criteria: {}", criteria);
    if (criteria != null) {
      final String sql = RqTaskSearchCriteria.taskSearchSqlBuilder()
        .withTitle(criteria.title())
        .withUsername(criteria.username())
        .withStatus(criteria.status())
        .withPriority(criteria.priority())
        .withEstimate(criteria.estimate())
        .build();
      return this.tasks.byCriteria(sql);
    }
    return this.all();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> withPriority(final int priority) {
    log.debug("find tasks with priority {}", priority);
    return this.tasks.withPriority(priority);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> withStatus(final String status) {
    log.debug("find tasks with status {}", status);
    return this.tasks.withStatus(status);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> openAndAssigned() {
    return this.tasks.openAndAssigned();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> all() {
    log.debug("find all tasks");
    return this.tasks.all();
  }

  @Override
  @Transactional
  public void add(final Task task) {
    log.debug("add new task {}", task);
    this.tasks.add(task);
  }

  @Override
  @Transactional
  public Task update(final Long id, @NotNull final RqTaskUpdate request) {
    log.debug("update task with id {}, request: {}", id, request);
    final String sql = RqTaskUpdate.taskUpdateSqlBuilder()
      .withTitle(request.title())
      .withAbout(request.about())
      .withAssignee(request.username())
      .withStatus(request.status())
      .withPriority(request.priority())
      .withDue(request.due())
      .withTracked(request.tracked())
      .build(id);
    log.debug("task update SQL generated: {}", sql);
    this.tasks.update(sql);
    return this.byId(id);
  }

  @Override
  @Transactional
  public Task update(final Long id, final String status) {
    log.debug("set status {} for ticket {}", status, id);
    this.tasks.update(id, status);
    return this.byId(id);
  }

  @Override
  @Transactional
  public Task assign(final Long id, final Long user) {
    log.debug("assign user {} to ticket {}", user, id);
    this.tasks.assign(id, user);
    return this.byId(id);
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("delete ticket by id {}", id);
    this.comments.byTask(id)
      .forEach(comment ->
        this.comments.delete(
          comment.id()
        )
      );
    this.tasks.delete(id);
  }

  @Override
  @Transactional
  public Task track(final Long id, final Integer minutes) {
    log.debug("track {} minutes to ticket {}", minutes, id);
    this.tasks.update(id, minutes);
    return this.byId(id);
  }

}
