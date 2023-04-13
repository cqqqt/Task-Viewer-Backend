package com.taskviewer.api.web.rq;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record RqTaskSearchCriteria(
	String title,
	String username,
	String status,
	Integer priority,
	LocalDateTime estimate) {

	@Contract(value = " -> new", pure = true)
	public static @NotNull TaskSearchSqlBuilder taskSearchSqlBuilder() {
		return new TaskSearchSqlBuilder();
	}

	public static class TaskSearchSqlBuilder {
		private final StringBuilder sql = new StringBuilder("""
			SELECT t.id AS task_id,
			    t.title AS title,
			    t.about AS about,
			    t.status AS status,
			    t.priority AS priority,
			    t.estimate AS estimate,
			    t.tracked AS tracked,
			    t.created as task_created,

			    l.username as username
			FROM task t INNER JOIN login l on l.id = t.assigne
			WHERE true""");

		public TaskSearchSqlBuilder withTitle(String title) {
			if(title != null) {
				sql.append(" and title = '")
					.append(title)
					.append("'");
			}
			return this;
		}

		public TaskSearchSqlBuilder withUsername(String username) {
			if(username != null) {
				sql.append(" and username = '")
					.append(username)
					.append("'");
			}
			return this;
		}

		public TaskSearchSqlBuilder withStatus(String status) {
			if(status != null) {
				sql.append(" and status = '")
					.append(status)
					.append("'");
			}
			return this;
		}

		public TaskSearchSqlBuilder withPriority(Integer priority) {
			if(priority != null) {
				sql.append(" and priority = ")
					.append(priority);
			}
			return this;
		}

		public TaskSearchSqlBuilder withEstimate(LocalDateTime estimate) {
			if(estimate != null) {
				sql.append(" and estimate = ")
					.append( Timestamp.valueOf(estimate) );
			}
			return this;
		}

		public String build() {
			return sql.toString();
		}

	}

}