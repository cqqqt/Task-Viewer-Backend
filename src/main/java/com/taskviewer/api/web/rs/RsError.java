package com.taskviewer.api.web.rs;

import java.util.Locale;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cactoos.text.FormattedText;

public interface RsError {

  String content() throws Exception;

  @Data
  @RequiredArgsConstructor
  final class WithCode implements RsError {

    private final int code;
    private final String message;

    @Override
    public String content() throws Exception {
      return new FormattedText(
        "Error code: %s, Message content: %s",
        Locale.ROOT,
        this.code,
        this.message
      ).asString();
    }
  }
}
