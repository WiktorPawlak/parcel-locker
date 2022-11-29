package pl.pas.parcellocker.repositories;

import pl.pas.parcellocker.model.Client;

public class ClientRedisRepository extends AbstractRedisRepository<Client> {

    public ClientRedisRepository() {
        super(Client.class);
    }

}
