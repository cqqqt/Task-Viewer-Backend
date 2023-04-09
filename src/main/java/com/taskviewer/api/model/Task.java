package com.taskviewer.api.model;

public interface Task {

  Long id();

  String title();

  String about();

  Status status();

  TimeEstimate time();

  Long user();
}