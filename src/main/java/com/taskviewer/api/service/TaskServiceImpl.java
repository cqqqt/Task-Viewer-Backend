package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TaskNotFoundException;
import com.taskviewer.api.model.Tasks;
import com.taskviewer.api.web.rq.RqTaskSearchCriteria;
import com.taskviewer.api.web.rq.RqTaskUpdate;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final Tasks tasks;

  @Override
  @Transactional(readOnly = true)
  public Task byId(final Long id) {
    return this.tasks.byId(id).orElseThrow(TaskNotFoundException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> byUsername(final String username) {
    return this.tasks.byUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> byEmail(final String email) {
    return this.tasks.byEmail(email);
  }

  @Override
  @Transactional
  public List<Task> byCriteria(@NotNull final RqTaskSearchCriteria criteria) {
    final String sql = RqTaskSearchCriteria.taskSearchSqlBuilder()
      .withTitle(criteria.title())
      .withUsername(criteria.username())
      .withStatus(criteria.status())
      .withPriority(criteria.priority())
      .withEstimate(criteria.estimate())
      .build();
    return this.tasks.byCriteria(sql);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> withPriority(final int priority) {
    return this.tasks.withPriority(priority);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> withStatus(final String status) {
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
    return this.tasks.all();
  }

  @Override
  @Transactional
  public Task add(final Task task) {
    this.tasks.add(task);
    return this.byId(task.id());
  }

  @Override
  @Transactional
  public Task update(final Long id, @NotNull final RqTaskUpdate request) {
    final String sql = RqTaskUpdate.taskUpdateSqlBuilder()
      .withTitle(request.title())
      .withAbout(request.about())
      .withAssignee(request.username())
      .withStatus(request.status())
      .withPriority(request.priority())
      .withEstimate(request.estimate())
      .withTracked(request.tracked())
      .build(id);
    this.tasks.update(sql);
    return this.byId(id);
  }

  @Override
  @Transactional
  public Task assign(final Long id, final Long user) {
    this.tasks.assign(id, user);
    return this.byId(id);
  }

}
