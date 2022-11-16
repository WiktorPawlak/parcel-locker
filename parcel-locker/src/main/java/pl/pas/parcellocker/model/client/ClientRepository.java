package pl.pas.parcellocker.model.client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    void add(Client client);

    void archive(UUID clientId);

    List<Client> findAll();

    Optional<Client> findByTelNumber(String telNumber);

    List<Client> findByTelNumberPart(String telNumberPart);
}
