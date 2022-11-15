package pl.pas.parcellocker.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
public class JakartaContainerInitializer {

    static final String PACKAGE_NAME = "parcel-locker-1.0-SNAPSHOT.war";
    static final String CONTAINER_DEPLOYMENT_PATH = "/opt/payara/deployments/";
    static final int PORT = 8080;

    static Network network = Network.newNetwork();

    private static final String DB_NAME = "database";
    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName
        .parse("postgres")
        .withTag("15.0-alpine");

    @Container
    static final PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(DEFAULT_IMAGE_NAME)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD)
            .withNetwork(network)
            .withNetworkAliases("postgres");

    @Container
    static final GenericContainer<?> jakartaApp = new GenericContainer<>(DockerImageName.parse("payara/micro:5.2022.3-jdk11"))
        .withExposedPorts(PORT)
        .withCopyFileToContainer(
            MountableFile.forHostPath("target/" + PACKAGE_NAME),
            CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME)
        .withCommand("--deploy " + CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME + " --contextRoot /")
        .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
        .withNetwork(network)
        .dependsOn(postgres);

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void setup() {
        String baseUri = "http://" +
            jakartaApp.getHost() + ":" +
            jakartaApp.getMappedPort(PORT) + "/";

        requestSpecification = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .build();
    }
}
