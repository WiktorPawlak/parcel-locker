//package pl.pas.parcellocker.repositories;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pl.pas.parcellocker.model.Client;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class ClientMongoRepositoryTest {
//    ClientDao abstractMongoRepository = new ClientDao();
//    Client client1;
//
//    @BeforeEach
//    void setup() {
//        client1 = new Client("Mati", "Kowal", "12345678");
//    }
//
//    @Test
//    void shouldAddClient() {
//       abstractMongoRepository.add(client1);
//
//        assertEquals(getClientFromRepo(client1), client1);
//    }
//
//    @Test
//    void shouldUpdateClient() {
//        abstractMongoRepository.add(client1);
//        client1.setActive(false);
//        abstractMongoRepository.update(client1);
//
//        assertFalse(getClientFromRepo(client1).isActive());
//    }
//
//    @Test
//    void shouldDeleteClient() {
//        abstractMongoRepository.add(client1);
//
//        abstractMongoRepository.delete(client1.getId());
//
//        assertNull(getClientFromRepo(client1));
//    }
//
//    @Test
//    void shouldReturnAllClients() {
//        abstractMongoRepository.add(client1);
//        abstractMongoRepository.add(new Client("test", "test", "test"));
//
//        assertTrue(abstractMongoRepository.findAll().size() >= 2);
//    }
//
//    private Client getClientFromRepo(Client client) {
//        return abstractMongoRepository.findById(client.getId());
//    }
//
//}
