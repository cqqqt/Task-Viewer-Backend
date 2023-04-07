package com.taskviewer.api.model;

public interface Comment {

  Comment withId(Long id);

  Long id();

  String content();

  String username();

  Long task();
}
