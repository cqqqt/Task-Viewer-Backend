package com.taskviewer.api.model;

public interface Task {

  Long id();

  String username();

  String title();

  String about();

  Status status();

  TimeEstimate time();

}