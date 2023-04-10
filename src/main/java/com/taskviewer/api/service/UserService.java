package com.taskviewer.api.service;

import com.taskviewer.api.model.User;

import java.util.List;

public interface UserService {

  User with(User user);

  User update(User user);

  User byId(Long id);

  User byUsername(String username);

  String password(User user);

  boolean userExists(String email, String username);

  List<User> iterate(UserSearchCriteria criteria);
}
