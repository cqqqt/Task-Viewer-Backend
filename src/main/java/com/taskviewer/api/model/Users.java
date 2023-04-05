package com.taskviewer.api.model;

public interface Users {

  void add(User user);

  User user(Long id) throws UserNotFoundException;

  User user(String username);

  User byEmail(String email);

  Iterable<User> iterate(String firstname, String lastname);

  Iterable<User> iterate();
}
