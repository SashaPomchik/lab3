package com.example.demo.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseIntegrationTest {

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @BeforeAll
    static void startContainer() {
        POSTGRESQL_CONTAINER.start();
        System.setProperty("TEST_DB_URL", POSTGRESQL_CONTAINER.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("TEST_DB_PASSWORD", POSTGRESQL_CONTAINER.getPassword());
    }
}
