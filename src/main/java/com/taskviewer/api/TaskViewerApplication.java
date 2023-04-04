package com.taskviewer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry class.
 */
@SpringBootApplication
public class TaskViewerApplication {

  /**
   * Entry point.
   *
   * @param args Application arguments.
   */
  public static void main(final String... args) {
    SpringApplication.run(TaskViewerApplication.class, args);
  }
}