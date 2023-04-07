package com.taskviewer.api.scheduled;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import com.taskviewer.api.service.MailService;
import com.taskviewer.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpirationEmailScheduler implements Scheduler {

    private final MailService mailService;
    private final UserService userService;
    private final Tasks tasks; //todo TaskService

    @Override
    @Scheduled(fixedDelay = 1800000)
    public void schedule() {
        List<Task> list = new ArrayList<>(); //todo this.taskService.iterate()
        list.forEach(task -> {
            long full = Duration.between(
                            task.time().tracked(), task.time().estimate())
                    .getSeconds();
            long left = Duration.between(
                            LocalDateTime.now(), task.time().estimate())
                    .getSeconds();
            if (left <= 0.2 * full) {
                this.mailService.send(this.userService.byId(task.user()), "Expiration email",
                        "Your task '" + task.title() + "' will be expired soon.");
            }
        });
    }

}
