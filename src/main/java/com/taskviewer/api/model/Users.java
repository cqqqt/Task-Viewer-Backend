package com.taskviewer.api.model;

import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import java.util.List;

public interface Users {

  void add(RqUser request);

  void update(Long id, RqUserUpdate request);

  User user(Long id);

  User user(String username);

  User byEmail(String email);

  List<User> iterate(String firstname, String lastname);

  List<User> iterate();
}
