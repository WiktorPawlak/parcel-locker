package pl.pas.parcellocker.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;
import org.testcontainers.utility.DockerImageName;

public class PostgresContainerInitializer {
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    private static final PostgreSQLContainer postgresContainer =
        new PostgreSQLContainer(DEFAULT_IMAGE_NAME)
            .withDatabaseName("database")
            .withUsername("admin")
            .withPassword("admin");

    static void start() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
            postgresContainer.waitingFor(new HostPortWaitStrategy());
            System.setProperty("db.port", postgresContainer.getFirstMappedPort().toString());
        }
    }
}
