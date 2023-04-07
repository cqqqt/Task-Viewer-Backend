package com.taskviewer.api.model;

import java.util.List;
import java.util.Optional;

public interface Users {

  void add(User user);

  void update(User user);

  Optional<User> byId(Long id);

  Optional<User> byUsername(String username);

  Optional<User> byEmail(String email);

  List<User> iterate(String firstname, String lastname);

  List<User> iterate();
}
