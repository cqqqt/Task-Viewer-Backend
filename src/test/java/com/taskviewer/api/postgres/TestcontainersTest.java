package com.taskviewer.api.postgres;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@Sql({"schema.sql", "test-data.sql"})
public abstract class TestcontainersTest {

	public static final PostgreSQLContainer<PostgresContainer> postgresContainer =
			PostgresContainer.getInstance();

	@BeforeAll
	static void run() {
		postgresContainer.start();
	}

	@AfterAll
	static void stop() {
		postgresContainer.stop();
	}

}
