package pl.pas.parcellocker.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.util.List;

import static pl.pas.parcellocker.config.PostgresContainerInitializer.DB_NAME;
import static pl.pas.parcellocker.config.PostgresContainerInitializer.DB_PASSWORD;
import static pl.pas.parcellocker.config.PostgresContainerInitializer.DB_USERNAME;
import static pl.pas.parcellocker.config.PostgresContainerInitializer.POSTGRES_IMAGE;

@Testcontainers
public class JakartaContainerInitializer {

    private static final int PORT = 8080;
    private static final String PACKAGE_NAME = "parcel-locker-1.0-SNAPSHOT.war";
    private static final String CONTAINER_DEPLOYMENT_PATH = "/opt/payara/deployments/";
    private static final DockerImageName PAYARA_IMAGE = DockerImageName
        .parse("payara/micro")
        .withTag("5.2022.3-jdk11");

    private static final Network network = Network.newNetwork();

    @Container
    static final PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD)
            .withNetwork(network)
            .withNetworkAliases("postgres");

    @Container
    protected static GenericContainer<?> jakartaApp = new GenericContainer<>(PAYARA_IMAGE)
        .withExposedPorts(PORT)
        .withEnv("DB_HOST_PORT", "postgres:5432")
        .withCopyFileToContainer(
            MountableFile.forHostPath("target/" + PACKAGE_NAME),
            CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME)
        .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
        .withNetwork(network)
        .dependsOn(postgres)
        .withCommand("--deploy " + CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME + " --contextRoot /");

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void setup() {
        String baseUri = "http://" +
            jakartaApp.getHost() + ":" +
            jakartaApp.getMappedPort(PORT);

        requestSpecification = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .build();
    }
}
