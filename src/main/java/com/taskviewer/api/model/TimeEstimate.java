package com.taskviewer.api.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public interface TimeEstimate {

  LocalDateTime due();

  int tracked();

  @RequiredArgsConstructor
  final class InMinutes implements TimeEstimate {

    private final LocalDateTime due;
    private final int tracked;

    @Override
    public LocalDateTime due() {
      return this.due;
    }

    @Override
    public int tracked() {
      return this.tracked;
    }
  }
}
