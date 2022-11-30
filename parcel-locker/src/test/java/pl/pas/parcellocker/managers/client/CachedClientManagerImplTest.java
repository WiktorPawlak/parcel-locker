package pl.pas.parcellocker.managers.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CachedClientManagerImplTest extends TestsConfig {

    private final CachedClientManagerImpl cachedClientManager = new CachedClientManagerImpl();

    @AfterEach
    void finish() {
        clientRedisRepository.clear();
        clientRepository.findAll().forEach(client -> clientRepository.delete(client.getId()));
    }

    @Test
    void Should_UpdateCache_WhenUnregisterClient() {
        Client client = new Client("asd-test", "asd-test", "123");
        clientRepository.add(client);
        clientRedisRepository.add(client);

        cachedClientManager.unregisterClient(client);

        Client cachedClient = cachedClientManager.getClient("123");
        Client unregisteredClient = clientRepository.findByTelNumber(client.getTelNumber());

        assertEquals(unregisteredClient, cachedClient);
    }

    @Test
    void Should_RetrieveClient_WhenDataInCache() {
        Client client = new Client("asd-test", "asd-test", "123");
        clientRedisRepository.add(client);

        Client cachedClient = cachedClientManager.getClient("123");

        assertEquals(client, cachedClient);
    }

    @Test
    void Should_RetrieveClient_WhenDataInDbButNotInCache() {
        Client client = new Client("asd-test", "asd-test", "123");
        clientRepository.add(client);

        Client cachedClient = cachedClientManager.getClient("123");

        assertEquals(client, cachedClient);
    }

    @Test
    void Should_NotRetrieveClient_WhenCacheClearedAndDataNotInDb() {
        assertNull(cachedClientManager.getClient("123"));
    }

    //@Test
    void Should_RetrieveClient_WhenCacheUnavailableButDataInDb() {
        Client client = new Client("asd-test", "asd-test", "123");
        clientRepository.add(client);

        Client cachedClient = cachedClientManager.getClient("123");

        assertEquals(client, cachedClient);
    }
}
