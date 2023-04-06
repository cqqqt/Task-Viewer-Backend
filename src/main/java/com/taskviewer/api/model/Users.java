package com.taskviewer.api.model;

import com.taskviewer.api.web.rq.RqUser;

public interface Users {

  void add(RqUser user);

  User user(Long id) throws UserNotFoundException;

  User user(String username);

  User byEmail(String email);

  Iterable<User> iterate(String firstname, String lastname);

  Iterable<User> iterate();
}
