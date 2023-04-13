package com.taskviewer.api.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public interface TimeEstimate {

  LocalDateTime estimate();

  LocalDateTime tracked();

  @RequiredArgsConstructor
  final class InMinutes implements TimeEstimate {

    private final LocalDateTime estimate;
    private final LocalDateTime tracked;

    @Override
    public LocalDateTime estimate() {
      return this.estimate;
    }

    @Override
    public LocalDateTime tracked() {
      return this.tracked;
    }
  }
}
