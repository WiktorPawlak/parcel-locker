package pl.pas.parcellocker.controllers;


import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.ClientController;
import pl.pas.parcellocker.api.dto.ClientDto;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

@Path(value = "/clients")
public class ClientControllerImpl implements ClientController {

    @Inject
    private UserManager userManager;

    public Response getAllClients() {
        try {
            return Response.ok().entity(userManager.findAll()).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response getClient(String telNumber) {
        try {
            return Response.ok().entity(userManager.getUser(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response getClientsByPhoneNumberPattern(String telNumber) {
        try {
            return Response.ok().entity(userManager.getUsersByPartialTelNumber(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response registerClient(ClientDto clientDTO) {
        try {
            User newUser = userManager.registerClient(clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber);
            return Response.status(Response.Status.CREATED).entity(newUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    public Response unregisterClient(String telNumber) {
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

    public Response editClient(UUID id, User user) {
        try {
            userManager.edit(id, user);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            //TODO tu chyba nie powinien być conflict
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
}
