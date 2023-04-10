package com.taskviewer.api.web.controller;

import com.taskviewer.api.postgres.PgUser;
import com.taskviewer.api.service.UserSearchCriteria;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import com.taskviewer.api.web.rs.RsUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService users;

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PutMapping
  public RsUser update(
    @AuthenticationPrincipal final User principal,
    @RequestBody final RqUserUpdate request) {
    return new RsUser(
      this.users.update(
        PgUser.builder()
          .id(
            this.users
              .byUsername(principal.getUsername())
              .id()
          )
          .firstname(request.firstname())
          .lastname(request.lastname())
          .build()
      )
    );
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping("/@{username}")
  public RsUser byUsername(@PathVariable final String username) {
    return new RsUser(this.users.byUsername(username));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @GetMapping
  public List<RsUser> iterate(final UserSearchCriteria criteria) {
    return this.users.iterate(criteria)
      .stream()
      .map(RsUser::new)
      .toList();
  }
}
