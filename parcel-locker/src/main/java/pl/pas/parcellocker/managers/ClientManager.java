package pl.pas.parcellocker.managers;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.ClientRepository;

import java.util.Arrays;

public class ClientManager {
    private static final Logger logger = LoggerFactory.getLogger(ClientManager.class);

    private final ClientRepository clientRepository;

    public ClientManager() {
        clientRepository = new ClientRepository();
    }

    public Client getClient(String telNumber) {
        validateIfEmpty(telNumber);

        return clientRepository.findByTelNumber(telNumber);
    }

    public Client registerClient(String firstName, String lastName, String telNumber) {
        for (Client client : clientRepository.findAll()) {
            if(client.getTelNumber().equals(telNumber))
                return client;
        }

        Client newClient = new Client(firstName, lastName, telNumber);
        clientRepository.add(newClient);
        return newClient;
    }

    public Client unregisterClient(Client client) {
        if (client == null)
            throw new ClientManagerException("client is a nullptr!");

        try {
            getClient(client.getTelNumber());
        } catch (NotFoundException exception) {
            logger.error(exception.getMessage());
        }

        client.setArchive(true);
        return client;
    }

    private void validateIfEmpty(String... values) {
        if (Arrays.stream(values).anyMatch(String::isEmpty))
            throw new ClientManagerException("Value is empty!");
    }
}
