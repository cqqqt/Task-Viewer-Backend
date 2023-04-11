package com.taskviewer.api.model;

public interface User {

  Long id();

  String username();

  Role role();

  String email();

  String firstname();

  String lastname();

  String password();
}