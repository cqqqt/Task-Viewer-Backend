package com.taskviewer.api.service;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final Users users;

  @Transactional
  @Override
  public User with(final User user) {
    log.debug("add user {}", user);
    this.users.add(user);
    return this.byUsername(user.username());
  }

  @Transactional
  @Override
  public User update(final User user) {
    log.debug("update user {} with id {}", user, user.id());
    this.users.update(user);
    return this.byId(user.id());
  }

  @Override
  public User byId(final Long id) {
    log.debug("find user by id {}", id);
    return this.users.byId(id)
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with id %s is not found"
              .formatted(
                id
              )
          )
      );
  }

  @Override
  public User byUsername(final String username) {
    log.debug("find user by username {}", username);
    return this.users.byUsername(username)
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with username %s is not found"
              .formatted(
                username
              )
          )
      );
  }

  @Override
  public String password(final User user) {
    log.debug("fetch password from user {}", user);
    return this.users.password(user.id())
      .orElseThrow(
        () ->
          new UserNotFoundException(
            "User with id %s is not found"
              .formatted(
                user.id()
              )
          )
      );
  }

  @Override
  public boolean userExists(final String email, final String username) {
    return this.users.exists(email, username);
  }

  @Override
  public List<User> iterate(final UserSearchCriteria criteria) {
    log.debug("user search criteria: {}", criteria);
    if (criteria.firstname() != null && criteria.lastname() != null) {
      return this.users.iterate(criteria.firstname(), criteria.lastname());
    } else {
      return this.iterate();
    }
  }

  @Override
  public List<User> iterate() {
    return this.users.iterate();
  }
}
