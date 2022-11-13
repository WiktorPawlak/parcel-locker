package pl.pas.parcellocker;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import pl.pas.parcellocker.managers.ClientManager;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.client.ClientRepository;
import pl.pas.parcellocker.repositories.hibernate.ClientRepositoryHibernate;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        ClientRepository clientRepository = new ClientRepositoryHibernate();
        ClientManager clientManager = new ClientManager(clientRepository);
        Client client = clientManager.registerClient("test", "test", "test");
        return client.toString();
    }
}
