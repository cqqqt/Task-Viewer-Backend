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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
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
    log.debug("all open and assigned tasks: {}", all);
    all.forEach(
      task -> {
        final Duration between = Duration.between(
          LocalDateTime.now(),
          task.time().due()
        );
        if (!LocalDateTime.now().isAfter(task.time().due())
          && between.toDays() < 1L) {
          final String email = this.users.byUsername(
            task.username()
          ).email();
          log.debug("expiration email will be sent to {}", email);
          this.mails.send(
            email,
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
