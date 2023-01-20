package pl.pas.parcellocker.api;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.dto.ClientDto;
import pl.pas.parcellocker.model.user.User;


@Path(value = "/clients")
public interface ClientController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllClients();

    @GET
    @Path("/{telNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getClient(@PathParam("telNumber") String telNumber);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getClientsByPhoneNumberPattern(@QueryParam("telNumber") String telNumber);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerClient(@Valid ClientDto clientDTO);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response editClient(UUID id, User user);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.TEXT_PLAIN})
    Response unregisterClient(String telNumber);
}
