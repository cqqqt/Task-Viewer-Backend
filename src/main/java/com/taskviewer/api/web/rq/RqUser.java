package com.taskviewer.api.web.rq;

public record RqUser(
  String username,
  String firstname,
  String lastname,
  String password,
  String role,
  String email
) {
}