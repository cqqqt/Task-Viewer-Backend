package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Status;
import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TimeEstimate;
import lombok.Builder;

@Builder
public class PgTask implements Task {

	private Long id;
	private String username;
	private String title;
	private String about;
	private Status status;
	private TimeEstimate time;

	@Override
	public Long id() {
		return this.id;
	}

	@Override
	public String username() {
		return this.username;
	}

	@Override
	public String title() {
		return this.title;
	}

	@Override
	public String about() {
		return this.about;
	}

	@Override
	public Status status() {
		return this.status;
	}

	@Override
	public TimeEstimate time() {
		return this.time;
	}

}
