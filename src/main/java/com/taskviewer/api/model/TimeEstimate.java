package com.taskviewer.api.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public interface TimeEstimate {

  LocalDateTime due();

  LocalDateTime tracked();

  @RequiredArgsConstructor
  final class InMinutes implements TimeEstimate {

    private final LocalDateTime due;
    private final LocalDateTime tracked;

    @Override
    public LocalDateTime due() {
      return this.due;
    }

    @Override
    public LocalDateTime tracked() {
      return this.tracked;
    }
  }
}
