package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final Users users;

  @GetMapping("{id}")
  public User user(@PathVariable final Long id) {
    return this.users.user(id);
  }
}
