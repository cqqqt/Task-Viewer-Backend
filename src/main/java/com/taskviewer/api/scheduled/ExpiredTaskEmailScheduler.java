package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredTaskEmailScheduler implements Scheduler {

  private final TaskService tasks;
  private final UserService users;
  private final MailService mails;

  @Override
  @Scheduled(fixedDelay = 3600000L)
  public void schedule() {
    final List<Task> all = this.tasks.openAndAssigned();
    log.debug("all open and assigned tasks :{}", all);
    all.forEach(
      task -> {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime estimate = task.time().due();
        if (now.isAfter(estimate)) {
          if (task.reporter() != null) {
            final String reporter = task.reporter();
            log.debug(
              "task expired email will be sent to reporter {}",
              reporter
            );
            this.mails.send(
              reporter,
              "@CC Task %s is expired"
                .formatted(
                  task.title()
                ),
              "@CC Task %s is expired"
                .formatted(
                  task.title()
                )
            );
          }
          final String email = this.users.byUsername(
            task.username()
          ).email();
          log.debug("task expired email will be sent to {}", email);
          this.mails.send(
            email,
            "Task %s is expired"
              .formatted(
                task.title()
              ),
            "Task %s is expired"
              .formatted(
                task.title()
              )
          );
        }
      }
    );
  }
}
