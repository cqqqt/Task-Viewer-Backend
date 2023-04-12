package com.taskviewer.api.web.rs;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JsonError implements RsError {

  private final String content;

  @Override
  public String content() throws Exception {
    return this.content;
  }
}
