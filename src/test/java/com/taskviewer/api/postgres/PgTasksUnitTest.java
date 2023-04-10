package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Status;
import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.TimeEstimate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PgTasksUnitTest {

	@Mock
	private JdbcTemplate jdbc;

	@Mock
	private ViewTask view;

	@InjectMocks
	private PgTasks pgTasks;

	@Test
	void verifiesById() {
		long id = 1;
		Task task = PgTask.builder()
				.id( id )
				.title("Test")
				.about("Task for test")
				.username("test-username")
				.status(new Status.Simple("HIGH", 1) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		when( jdbc.query(PgTasks.FIND_BY_ID, view, id) ).thenReturn( Collections.singletonList(task) );

		assertEquals(Optional.of(task), pgTasks.byId(1L));
		verify(jdbc).query(PgTasks.FIND_BY_ID, view, id);
	}

	@Test
	void verifiesNotFoundById() {
		long id = 0;
		when( jdbc.query(PgTasks.FIND_BY_ID, view, id )).thenReturn( Collections.emptyList() );

		assertEquals(Optional.empty(), pgTasks.byId(id));
		verify(jdbc).query(PgTasks.FIND_BY_ID, view, id);
	}

	@Test
	void verifiesByUsername() {
		String username = "test-username";
		Task task1 = PgTask.builder()
				.id( 1L )
				.title("Test")
				.about("Task for test #1")
				.username(username)
				.status(new Status.Simple("HIGH", 1) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		Task task2 = PgTask.builder()
				.id( 2L )
				.title("Test")
				.about("Task for test #2")
				.username(username)
				.status(new Status.Simple("MEDIUM", 2) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()) )
				)
				.build();
		List<Task> tasks = new ArrayList<>(2);
		tasks.add(task1);
		tasks.add(task2);
		when( jdbc.query(PgTasks.FIND_BY_USERNAME, view, username)).thenReturn(tasks);

		assertEquals(tasks, pgTasks.byUsername(username));
		verify(jdbc).query(PgTasks.FIND_BY_USERNAME, view, username);
	}

	@Test
	void verifiesByEmail() {
		Task task1 = PgTask.builder()
				.id( 1L )
				.title("Test")
				.about("Task for test #1")
				.username("test-username")
				.status(new Status.Simple("HIGH", 1) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		Task task2 = PgTask.builder()
				.id( 2L )
				.title("Test")
				.about("Task for test #2")
				.username("test-username")
				.status(new Status.Simple("MEDIUM", 2) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()) )
				)
				.build();
		List<Task> tasks = new ArrayList<>(2);
		tasks.add(task1);
		tasks.add(task2);
		String email = "testEmail";
		when( jdbc.query(PgTasks.FIND_BY_EMAIL, view, email)).thenReturn(tasks);

		assertEquals(tasks, pgTasks.byEmail(email));
		verify(jdbc).query(PgTasks.FIND_BY_EMAIL, view, email);
	}

	@Test
	void verifiesWithPriority() {
		int priority = 1;
		String value = "HIGH";
		Task task1 = PgTask.builder()
				.id( 1L )
				.title("Test")
				.about("Task for test #1")
				.username("test-username")
				.status(new Status.Simple(value, priority) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		Task task2 = PgTask.builder()
				.id( 2L )
				.title("Test")
				.about("Task for test #2")
				.username("test-username")
				.status(new Status.Simple(value, priority) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()) )
				)
				.build();
		List<Task> tasks = new ArrayList<>(2);
		tasks.add(task1);
		tasks.add(task2);
		when( jdbc.query(PgTasks.FIND_WITH_PRIORITY, view, priority)).thenReturn(tasks);

		assertEquals(tasks, pgTasks.withPriority(priority));
		verify(jdbc).query(PgTasks.FIND_WITH_PRIORITY, view, priority);
	}

	@Test
	void verifiesWithStatus() {
		int priority = 1;
		String value = "HIGH";
		Task task1 = PgTask.builder()
				.id( 1L )
				.title("Test")
				.about("Task for test #1")
				.username("test-username")
				.status(new Status.Simple(value, priority) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		Task task2 = PgTask.builder()
				.id( 2L )
				.title("Test")
				.about("Task for test #2")
				.username("test-username")
				.status(new Status.Simple(value, priority) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()) )
				)
				.build();
		List<Task> tasks = new ArrayList<>(2);
		tasks.add(task1);
		tasks.add(task2);
		when( jdbc.query(PgTasks.FIND_WITH_STATUS, view, value)).thenReturn(tasks);

		assertEquals(tasks, pgTasks.withStatus(value));
		verify(jdbc).query(PgTasks.FIND_WITH_STATUS, view, value);
	}

	@Test
	void verifiesFindAll() {
		Task task1 = PgTask.builder()
				.id( 1L )
				.title("Test")
				.about("Task for test #1")
				.username("test-username")
				.status(new Status.Simple("HIGH", 1) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(6000), ZoneOffset.systemDefault()) )
				)
				.build();
		Task task2 = PgTask.builder()
				.id( 2L )
				.title("Test")
				.about("Task for test #2")
				.username("test-username")
				.status(new Status.Simple("MEDIUM", 2) )
				.time(new TimeEstimate.InMinutes(
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()),
						LocalDateTime.ofInstant(Instant.ofEpochSecond(7200), ZoneOffset.systemDefault()) )
				)
				.build();
		List<Task> tasks = new ArrayList<>(2);
		tasks.add(task1);
		tasks.add(task2);
		when( jdbc.query(PgTasks.FIND_ALL, view)).thenReturn(tasks);

		assertEquals(tasks, pgTasks.all());
		verify(jdbc).query(PgTasks.FIND_ALL, view);
	}

}