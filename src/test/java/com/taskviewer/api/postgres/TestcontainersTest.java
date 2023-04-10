package com.taskviewer.api.postgres;

import com.taskviewer.api.TaskViewerApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = TaskViewerApplication.class)
@ExtendWith(SpringExtension.class)
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
