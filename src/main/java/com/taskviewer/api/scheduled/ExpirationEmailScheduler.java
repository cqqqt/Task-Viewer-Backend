package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
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
  @Scheduled(cron = "0 */12 * * * *")
  public void schedule() {
    final List<Task> all = this.tasks.openAndAssigned();
    all.forEach(
      task -> {
        final Duration between = Duration.between(
          LocalDateTime.now(),
          task.time().due()
        );
        if (!LocalDateTime.now().isAfter(task.time().due())
          && between.toDays() < 1L) {
          this.mails.send(
            this.users.byUsername(task.username()
            ).email(),
            "Task %s will be expired soon"
              .formatted(
                task.title()
              ),
            "Task %s will be expired soon"
              .formatted(
                task.title()
              )
          );
        }
      }
    );
  }
}
