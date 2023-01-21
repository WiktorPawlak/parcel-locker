package pl.pas.parcellocker.controllers;


import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

import java.util.UUID;

@Path(value = "/clients")
public class ClientController {

    @Inject
    private UserManager userManager;

    @GET
    @Path("/{telNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(userManager.getUser(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsByPhoneNumberPattern(@QueryParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(userManager.getUsersByPartialTelNumber(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerClient(@Valid ClientDto clientDTO) {
        try {
            User newUser = userManager.registerClient(clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber);
            return Response.status(Response.Status.CREATED).entity(newUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{telNumber}/archive")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.TEXT_PLAIN})
    public Response archiveClient(@PathParam("telNumber") String telNumber) {
        try {
            User user = userManager.getUser(telNumber);
            User unregisteredUser = userManager.unregisterClient(user);
            return Response.ok().entity(unregisteredUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{telNumber}/unarchive")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.TEXT_PLAIN})
    public Response unarchiveClient(@PathParam("telNumber") String telNumber) {
        try {
            User user = userManager.getUser(telNumber);
            User unregisteredUser = userManager.activateClient(user);
            return Response.ok().entity(unregisteredUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editClient(@PathParam(value = "id") UUID id, @Valid ClientDto clientDto) {
        try {
            userManager.edit(id, clientDto);
            User user = userManager.findById(id);
            return Response.ok().entity(user).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
