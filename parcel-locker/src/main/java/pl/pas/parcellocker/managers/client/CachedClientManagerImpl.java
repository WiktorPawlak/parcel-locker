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
        Client client = null;
        if (cache.isConnected()) {
            client = cache.findByTelNumber(telNumber);
        }

        if (client == null) {
            client = clientManager.getClient(telNumber);
            if (client != null && cache.isConnected()) {
                cache.add(client);
            }
        }
        return client;
    }

    @Override
    public Client registerClient(String firstName, String lastName, String telNumber) {
        return clientManager.registerClient(firstName, lastName, telNumber);
    }

    @Override
    public Client unregisterClient(Client client) {
        Client changedClient = clientManager.unregisterClient(client);
        cache.remove(client.getId());
        cache.add(client);
        return changedClient;
    }
}
