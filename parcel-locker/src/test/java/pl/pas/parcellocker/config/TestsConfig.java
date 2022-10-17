package pl.pas.parcellocker.config;

import org.junit.jupiter.api.BeforeAll;

public class TestsConfig {

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

}
