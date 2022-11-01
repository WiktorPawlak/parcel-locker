//package pl.pas.parcellocker.config;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.TestInstance;
////import pl.pas.parcellocker.repositories.ClientRepository;
////import pl.pas.parcellocker.repositories.DeliveryRepository;
//import pl.pas.parcellocker.repositories.LockerRepository;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class TestsConfig {
//
//   // protected final ClientRepository clientRepository = new ClientRepository();
//   // protected final DeliveryRepository deliveryRepository = new DeliveryRepository();
//    protected final LockerRepository lockerRepository = new LockerRepository();
//
//    @BeforeAll
//    static void beforeAll() {
//        PostgresContainerInitializer.start();
//    }
//
//    @AfterAll
//    void finisher() {
//        //deliveryRepository.findAll().forEach(deliveryRepository::remove);
//        //clientRepository.findAll().forEach(clientRepository::remove);
//        lockerRepository.findAll().forEach(lockerRepository::remove);
//    }
//
//}
