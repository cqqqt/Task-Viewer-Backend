package com.taskviewer.api.service;

import com.taskviewer.api.model.User;
import com.taskviewer.api.model.Users;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import com.taskviewer.api.web.rs.RsUser;
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
  public void create(final RqUser request) {
    this.users.add(request);
  }

  @Transactional
  @Override
  public RsUser update(final Long id, final RqUserUpdate request) {
    this.users.update(id, request);
    return new RsUser(this.users.user(id));
  }

  @Override
  public RsUser user(final String username) {
    final User user = this.users.user(username);
    return new RsUser(user);
  }

  @Override
  public List<RsUser> iterate(final UserSearchCriteria criteria) {
    return this.users.iterate(
        criteria.firstname(),
        criteria.lastname()
      )
      .stream()
      .map(RsUser::new)
      .toList();
  }

  @Override
  public List<RsUser> iterate() {
    return this.users.iterate()
      .stream()
      .map(RsUser::new)
      .toList();
  }
}
