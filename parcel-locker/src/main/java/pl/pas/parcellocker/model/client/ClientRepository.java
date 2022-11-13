package pl.pas.parcellocker.model.client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    void add(Client client);
    void archive(UUID clientId);
    List<Client> findAll();
    Client findByTelNumber(String telNumber);
}
