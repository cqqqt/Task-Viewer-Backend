package com.taskviewer.api.postgres;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

	private static final String IMAGE_VERSION = "postgres:15.2";
	private static PostgresContainer container;

	private PostgresContainer() {
		super(IMAGE_VERSION);
	}

	public static PostgresContainer getInstance() {
		if (container == null) {
			container = new PostgresContainer();
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("TEST_URL", container.getJdbcUrl());
		System.setProperty("TEST_USERNAME", container.getUsername());
		System.setProperty("TEST_PASSWORD", container.getPassword());
	}

	@Override
	public void stop() {
		//do nothing, JVM handles shut down
	}

}
