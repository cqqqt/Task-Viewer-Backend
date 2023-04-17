package com.taskviewer.api.web.controller;

import com.taskviewer.api.model.Authentication;
import com.taskviewer.api.model.jwt.JwtRs;
import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.service.AuthService;
import com.taskviewer.api.web.rq.RqUser;
import com.taskviewer.api.web.rs.RsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public JwtRs login(@RequestBody @Validated Authentication authentication) {
    return authService.login(authentication);
  }

  @PostMapping("/refresh")
  public JwtRs refresh(@RequestBody JwtToken token) {
    return authService.refresh(token);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public RsUser register(@RequestBody @Validated RqUser user) {
    return new RsUser(
      authService.register(user)
    );
  }
}
