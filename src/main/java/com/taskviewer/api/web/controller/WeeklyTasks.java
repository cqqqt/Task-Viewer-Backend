package com.taskviewer.api.web.controller;

import com.taskviewer.api.service.UserService;
import com.taskviewer.api.service.WeeklySchedule;
import com.taskviewer.api.web.rs.RsTask;
import com.taskviewer.api.web.security.jwt.JwtUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weekly")
@RequiredArgsConstructor
public class WeeklyTasks {

  private final UserService users;
  private final WeeklySchedule schedule;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping
  public List<RsTask> tasks(@AuthenticationPrincipal final JwtUserDetails principal) {
    return this.schedule.tasks(
        this.users.byUsername(principal.getUsername())
          .id()
      ).stream()
      .map(RsTask::new)
      .toList();
  }
}
