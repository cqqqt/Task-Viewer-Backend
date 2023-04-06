package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.Users;
import com.taskviewer.api.web.rq.RqUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final Users users;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void add(@RequestBody final RqUser request) {
    this.users.add(
      request
    );
  }

  @GetMapping("/@{username}")
  public User byUsername(@PathVariable final String username) {
    return this.users.user(username);
  }

  @GetMapping
  public Iterable<User> iterate(
    @RequestParam(required = false) final String firstname,
    @RequestParam(required = false) final String lastname) {
    if (firstname != null && lastname != null) {
      return this.users.iterate(firstname, lastname);
    } else {
      return this.users.iterate();
    }
  }
}
