package pl.pas.parcellocker.managers;

import java.util.Arrays;
import java.util.List;

import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.client.ClientRepository;

public class ClientManager {

    private final ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(String telNumber) {
        validateIfEmpty(telNumber);

        return clientRepository.findByTelNumber(telNumber);
    }

    public List<Client> getClientsByPartialTelNumber(String telNumberPart) {
        validateIfEmpty(telNumberPart);

        return clientRepository.findByTelNumberPart(telNumberPart);
    }

    public synchronized Client registerClient(String firstName, String lastName, String telNumber) {
        validateIfEmpty(firstName, lastName, telNumber);

        for (Client client : clientRepository.findAll()) {
            if (client.getTelNumber().equals(telNumber))
                return client;
        }

        Client newClient = new Client(firstName, lastName, telNumber);
        clientRepository.add(newClient);
        return newClient;
    }

    public Client unregisterClient(Client client) {
        if (client == null)
            throw new ClientManagerException("client is a null!");

        getClient(client.getTelNumber());
        clientRepository.archive(client.getId());
        return client;
    }

    private void validateIfEmpty(String... values) {
        if (Arrays.stream(values).anyMatch(String::isEmpty))
            throw new ClientManagerException("Value is empty!");
    }
}
