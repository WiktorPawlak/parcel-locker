package pl.pas.parcellocker.managers.client;

import pl.pas.parcellocker.managers.ClientManager;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.ClientRedisRepository;

public class CachedClientManagerImpl implements ClientManager {

    private final ClientRedisRepository cache;
    private final ClientManager clientManager;

    CachedClientManagerImpl() {
        cache = new ClientRedisRepository();
        clientManager = new ClientManagerImpl();
    }

    @Override
    public Client getClient(String telNumber) {
        Client client = cache.findByTelNumber(telNumber);

        if (client == null || cache.isConnected()) {
            client = clientManager.getClient(telNumber);
            cache.add(client);
        }
        return client;
    }

    @Override
    public Client registerClient(String firstName, String lastName, String telNumber) {
        return clientManager.registerClient(firstName, lastName, telNumber);
    }

    @Override
    public Client unregisterClient(Client client) {
        return clientManager.unregisterClient(client);
    }
}
