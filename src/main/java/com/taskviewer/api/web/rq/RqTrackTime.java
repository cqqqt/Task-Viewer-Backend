package com.taskviewer.api.web.rq;

import jakarta.validation.constraints.NotNull;

public record RqTrackTime(@NotNull Integer minutes) {
}
