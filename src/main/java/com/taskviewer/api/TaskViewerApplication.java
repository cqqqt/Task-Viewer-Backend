package com.taskviewer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Entry class.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
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