package com.taskviewer.api.service;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.UserAlreadyExistsException;
import com.taskviewer.api.model.UserNotFoundException;
import com.taskviewer.api.model.Users;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final Users users;

  @Transactional
  @Override
  public User with(final User user) {
    final String email = user.email();
    if (this.users.byEmail(email).isPresent()) {
      throw new UserAlreadyExistsException(
        "User with email %s already exists"
          .formatted(
            email
          )
      );
    }
    final String username = user.username();
    if (this.users.byUsername(username).isPresent()) {
      throw new UserAlreadyExistsException(
        "User with username %s already exists"
          .formatted(
            username
          )
      );
    } else {
      this.users.add(user);
      return this.byUsername(user.username());
    }
  }

  @Transactional
  @Override
  public User update(final User user) {
    this.users.update(user);
    return this.byId(user.id());
  }

  @Override
  public User byId(final Long id) {
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
    if (criteria.firstname() != null && criteria.lastname() != null) {
      return this.users.iterate(criteria.firstname(), criteria.lastname());
    } else {
      return this.users.iterate();
    }
  }
}
