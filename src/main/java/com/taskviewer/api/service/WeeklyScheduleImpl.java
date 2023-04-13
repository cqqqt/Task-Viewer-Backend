package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class WeeklyScheduleImpl implements WeeklySchedule {

  private final Tasks tasks;

  @Override
  public List<Task> tasks(final Long user) {
    return this.tasks.weeklyByUser(user);
  }
}
