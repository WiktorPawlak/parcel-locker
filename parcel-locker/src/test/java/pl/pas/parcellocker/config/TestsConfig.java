package pl.pas.parcellocker.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.repositories.hibernate.ClientRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig extends RepositoryConfig {

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

}
