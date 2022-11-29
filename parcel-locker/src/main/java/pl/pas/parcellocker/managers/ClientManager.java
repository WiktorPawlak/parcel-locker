package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.model.Client;

public interface ClientManager {
    Client getClient(String telNumber);
    Client registerClient(String firstName, String lastName, String telNumber);
    Client unregisterClient(Client client);
}
