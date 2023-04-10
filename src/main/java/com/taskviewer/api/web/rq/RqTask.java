package com.taskviewer.api.web.rq;

import com.taskviewer.api.model.Status;
import com.taskviewer.api.model.TimeEstimate;

public record RqTask(
		String username,
		String title,
		String about,
		Status status,
		TimeEstimate timeEstimate
) {
}
