//package pl.pas.parcellocker.config;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.TestInstance;
//import pl.pas.parcellocker.repositories.dao.ClientDao;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class TestsConfig {
//
//    protected final ClientDao clientRepository = new ClientDao();
//    protected final DeliveryMongoRepository deliveryRepository = new DeliveryMongoRepository();
//    protected final LockerMongoRepository lockerRepository = new LockerMongoRepository();
//
//    @AfterAll
//    void finisher() {
//        deliveryRepository.findAll().forEach(delivery -> deliveryRepository.delete(delivery.getId()));
//        clientRepository.findAll().forEach(client -> clientRepository.delete(client.getId()));
//        lockerRepository.findAll().forEach(locker -> lockerRepository.delete(locker.getId()));
//    }
//
//}
