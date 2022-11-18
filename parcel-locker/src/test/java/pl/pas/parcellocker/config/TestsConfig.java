package pl.pas.parcellocker.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import pl.pas.parcellocker.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.UserRepositoryHibernate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig {

    protected static RequestSpecification requestSpecification;
    protected final UserRepositoryHibernate clientRepository = new UserRepositoryHibernate();
    protected final DeliveryRepositoryHibernate deliveryRepository = new DeliveryRepositoryHibernate();
    protected final LockerRepositoryHibernate lockerRepository = new LockerRepositoryHibernate();

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
        requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .build();
    }

    @AfterAll
    void finisher() {
        deliveryRepository.findAll().forEach(deliveryRepository::remove);
        clientRepository.findAll().forEach(clientRepository::remove);
        lockerRepository.findAll().forEach(lockerRepository::remove);
    }

}
