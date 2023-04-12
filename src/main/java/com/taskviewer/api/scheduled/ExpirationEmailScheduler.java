package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpirationEmailScheduler implements Scheduler {

  private final MailService mails;
  private final UserService users;
  private final TaskService tasks;

  @Override
  @Scheduled(fixedDelay = 1800000L)
  public void schedule() {
    final List<Task> all = this.tasks.openAndAssigned();
    all.forEach(task -> {
      final long full = Duration.between(
        task.time().tracked(), task.time().estimate()
      ).getSeconds();
      final LocalDateTime now = LocalDateTime.now();
      final long left = Duration.between(
        now, task.time().estimate()
      ).getSeconds();
      if (left <= 0.2 * full && !now.isAfter(task.time().estimate())) {
        this.mails.send(
          this.users.byUsername(task.username()),
          "Task %s will be expired soon"
            .formatted(
              task.title()
            ),
          "Your task %s will be expired soon."
            .formatted(
              task.title()
            )
        );
      }
    });
  }
}
