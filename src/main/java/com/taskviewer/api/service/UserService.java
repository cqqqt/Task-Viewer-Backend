package com.taskviewer.api.service;

import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rq.RqUserUpdate;
import com.taskviewer.api.web.rs.RsUser;
import java.util.List;

public interface UserService {

  void create(RqUser request);

  RsUser update(Long id, RqUserUpdate request);

  RsUser user(String username);

  List<RsUser> iterate(UserSearchCriteria criteria);

  List<RsUser> iterate();
}
