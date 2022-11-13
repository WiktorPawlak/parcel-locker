package pl.pas.parcellocker.controllers;


import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.managers.ClientManager;
import pl.pas.parcellocker.model.client.Client;

@Path(value = "/clients")
public class ClientController {

    @Inject
    private ClientManager clientManager;

    @GET
    @Path("/{telNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(clientManager.getClient(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@Valid ClientDto clientDTO) {
        try {
            Client newClient = clientManager.registerClient(clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber);
            return Response.status(Response.Status.CREATED).entity(newClient).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    //Client getClient(String telNumber)


//    List<Client> getClientsByPartialTelNumber(String telNumberPart)
//    synchronized Client registerClient(String firstName, String lastName, String telNumber)
//
//    Client unregisterClient(Client client)

}
