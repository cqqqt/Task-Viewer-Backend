package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Tasks;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ExpirationEmailScheduler implements Scheduler {

    private final MailService mailService;
    private final UserService userService;
    private final Tasks tasks; //todo TaskService

    @Override
    @Scheduled(fixedDelay = 1800000)
    public void schedule() {
        this.tasks.iterate().forEach(task -> {
            long duration = Duration.between(task.time().tracked(), task.time().estimate()).getSeconds();
            long last = Duration.between(LocalDateTime.now(), task.time().estimate()).getSeconds();
            if (last <= 0.2 * duration) {
                this.mailService.send(userService.byId(task.user()), "Expiration email",
                        "Your task '" + task.title() + "' will be expired soon.");
            }
        });
    }

}
