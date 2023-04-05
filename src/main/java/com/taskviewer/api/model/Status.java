package com.taskviewer.api.model;

import lombok.RequiredArgsConstructor;

public interface Status {

  String value();

  int priority();

  @RequiredArgsConstructor
  final class Simple implements Status {

    private final String value;
    private final int priority;

    @Override
    public String value() {
      return this.value;
    }

    @Override
    public int priority() {
      return this.priority;
    }
  }
}
