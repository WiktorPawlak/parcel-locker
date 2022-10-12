package pl.pas.parcellocker.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.ClientRepository;

public class ClientManager {

    private ClientRepository clientRepository;

    public Client getClient(Long id) {
       throw new NotImplementedException("Not implemented");
    }

    public Long registerClient(Client client) {
        throw new NotImplementedException("Not implemented");
    }

    public Long unregisterClient(Long id) {
        throw new NotImplementedException("Not implemented");
    }
}
