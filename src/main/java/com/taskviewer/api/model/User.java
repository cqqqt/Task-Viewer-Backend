package com.taskviewer.api.model;

public interface User {

  Long id();

  String username();

  String role();

  String email();

  String firstname();

  String lastname();
}