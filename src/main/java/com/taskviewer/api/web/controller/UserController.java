package com.taskviewer.api.web.controller;

import com.taskviewer.api.postgres.PgUser;
import com.taskviewer.api.service.UserSearchCriteria;
import com.taskviewer.api.service.UserService;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import com.taskviewer.api.web.rs.RsUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService users;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public RsUser add(@RequestBody final RqUser request) {
    return new RsUser(
      this.users.with(
        PgUser.builder()
          .username(request.username())
          .email(request.email())
          .role(request.role())
          .firstname(request.firstname())
          .lastname(request.lastname())
          .password(request.password())
          .build()
      )
    );
  }

  @PutMapping("/{id}") // auth name
  public RsUser update(
    @PathVariable final Long id,
    @RequestBody final RqUserUpdate request) {
    return new RsUser(
      this.users.update(
        PgUser.builder()
          .id(id)
          .firstname(request.firstname())
          .lastname(request.lastname())
          .build()
      )
    );
  }

  @GetMapping("/@{username}")
  public RsUser byUsername(@PathVariable final String username) {
    return new RsUser(this.users.byUsername(username));
  }

  @GetMapping
  public List<RsUser> iterate(final UserSearchCriteria criteria) {
    return this.users.iterate(criteria)
      .stream()
      .map(RsUser::new)
      .toList();
  }
}
