package com.taskviewer.api.web.rq;

import java.time.LocalDateTime;

public record RqTask(
		String username,
		String title,
		String about,
		String status,
		int priority,
		LocalDateTime due
) {
}
