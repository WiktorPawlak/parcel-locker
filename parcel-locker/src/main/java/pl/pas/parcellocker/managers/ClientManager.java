//package pl.pas.parcellocker.managers;
//
//import pl.pas.parcellocker.exceptions.ClientManagerException;
//import pl.pas.parcellocker.model.Client;
//import pl.pas.parcellocker.repositories.ClientDao;
//
//import java.util.Arrays;
//
//public class ClientManager {
//
//    private final ClientDao clientRepository;
//
//    public ClientManager() {
//        clientRepository = new ClientDao();
//    }
//
//    public Client getClient(String telNumber) {
//        validateIfEmpty(telNumber);
//
//        return clientRepository.findByTelNumber(telNumber);
//    }
//
//    public Client registerClient(String firstName, String lastName, String telNumber) {
//        validateIfEmpty(firstName, lastName, telNumber);
//
//        for (Client client : clientRepository.findAll()) {
//            if (client.getTelNumber().equals(telNumber))
//                return client;
//        }
//
//        Client newClient = new Client(firstName, lastName, telNumber);
//        clientRepository.add(newClient);
//        return newClient;
//    }
//
//    public Client unregisterClient(Client client) {
//        if (client == null)
//            throw new ClientManagerException("client is a null!");
//
//        getClient(client.getTelNumber());
//
//        client.setActive(false);
//        clientRepository.update(client);
//        return client;
//    }
//
//    private void validateIfEmpty(String... values) {
//        if (Arrays.stream(values).anyMatch(String::isEmpty))
//            throw new ClientManagerException("Value is empty!");
//    }
//}
