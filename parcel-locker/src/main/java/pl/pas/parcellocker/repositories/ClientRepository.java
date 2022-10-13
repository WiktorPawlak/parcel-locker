package pl.pas.parcellocker.repositories;

import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Client;

import java.util.Optional;

public class ClientRepository extends Repository<Client> {
    public Client findByTelNumber(String telNumber) {
        return objects.stream()
            .filter(client -> client.getTelNumber().equals(telNumber))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Client Not Found"));
    }
}
