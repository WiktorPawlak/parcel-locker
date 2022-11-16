package pl.pas.parcellocker.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig extends RepositoryConfig {

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

}
