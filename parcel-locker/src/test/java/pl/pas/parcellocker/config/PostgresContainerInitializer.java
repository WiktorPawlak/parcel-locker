package pl.pas.parcellocker.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;
import org.testcontainers.utility.DockerImageName;

public class PostgresContainerInitializer {

    private static final String DB_NAME = "database";
    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    private static final PostgreSQLContainer postgresContainer =
        new PostgreSQLContainer(DEFAULT_IMAGE_NAME)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD);

    static void start() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
            postgresContainer.waitingFor(new HostPortWaitStrategy());
            String firstFoundPort = postgresContainer.getFirstMappedPort().toString();
            System.setProperty("db.port", firstFoundPort);
        }
    }
}
