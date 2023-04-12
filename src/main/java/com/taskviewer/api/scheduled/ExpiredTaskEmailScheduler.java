package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.TaskService;
import com.taskviewer.api.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    log.debug(
      "these tasks expired, emailing the assignees of them :{}",
      all
    );
    all.forEach(
      task -> {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime estimate = task.time().estimate();
        if (now.isAfter(estimate)) {
          this.mails.send(
            this.users.byUsername(task.username()),
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