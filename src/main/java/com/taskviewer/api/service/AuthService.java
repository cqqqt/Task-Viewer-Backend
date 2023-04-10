package com.taskviewer.api.service;

import com.taskviewer.api.model.Authentication;
import com.taskviewer.api.model.User;
import com.taskviewer.api.model.jwt.JwtRs;
import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.web.rq.RqUser;

public interface AuthService {

  JwtRs login(Authentication authentication);

  JwtRs refresh(JwtToken token);

  User register(RqUser user);
}
