package com.taskviewer.api.postgres;

import com.taskviewer.api.TaskViewerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TaskViewerApplication.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.yaml")
public class PgTasksIntegrationTest extends TestcontainersTest {

	@Autowired
	private PgTasks pgTasks;

	@Test
	void verifiesNotFoundById() {
		assertEquals(Optional.empty(), pgTasks.byId(0L));
	}

}
