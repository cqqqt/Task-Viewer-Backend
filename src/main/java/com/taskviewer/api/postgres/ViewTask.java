package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Status;
import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TimeEstimate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ViewTask implements RowMapper<Task> {

	@Override
	public Task mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
		return PgTask.builder()
				.id( rs.getLong("task_id") )
				.username( rs.getString("username") )
				.title( rs.getString( "title") )
				.about( rs.getString( "about") )
				.status(new Status.Simple(
						rs.getString("status"),
						rs.getInt("priority") )
				)
				.time(new TimeEstimate.InMinutes(
						rs.getTimestamp("estimate").toLocalDateTime(),
						rs.getTimestamp("tracked").toLocalDateTime() )
				)
				.created( rs.getTimestamp("task_created").toLocalDateTime() )
				.build();
	}

}
