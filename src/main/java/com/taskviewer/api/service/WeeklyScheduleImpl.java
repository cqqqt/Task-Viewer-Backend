package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class WeeklyScheduleImpl implements WeeklySchedule {

  private final Tasks tasks;

  @Override
  public List<Task> tasks(final Long user) {
    log.debug("composing weekly schedule for user {}", user);
    return this.tasks.weeklyByUser(user);
  }
}
