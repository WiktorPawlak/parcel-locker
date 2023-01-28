package pl.pas.parcellocker.controllers;


import java.security.Principal;
import java.util.UUID;

import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.controllers.dto.ClientEditDto;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.security.EntityTagSignatureProvider;
import pl.pas.parcellocker.security.EtagValidatorFilterBinding;

@Path(value = "/clients")
public class ClientController {

    @Context
    private SecurityContext securityContext;

    @Inject
    private EntityTagSignatureProvider entityTagSignatureProvider;

    @Inject
    private UserManager userManager;

    @GET
    @Path("/{telNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"UNAUTHORIZED"})
    public Response getClient(@PathParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(userManager.getUser(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMINISTRATOR"})
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
    @RolesAllowed({"UNAUTHORIZED"})
    public Response registerClient(@Valid ClientDto clientDTO) {
        try {
            User newUser = userManager.registerClient(clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber, clientDTO.password);
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
    @RolesAllowed({"ADMINISTRATOR"})
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
    @RolesAllowed({"ADMINISTRATOR"})
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
    @RolesAllowed({"ADMINISTRATOR"})
    public Response editClient(@PathParam(value = "id") UUID id, @Valid ClientEditDto clientEditDto) {
        try {
            userManager.edit(id, clientEditDto);
            User user = userManager.findById(id);
            return Response.ok().entity(user).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response getSelf() {
        try {
            Principal callerPrincipal = securityContext.getUserPrincipal();
            String callerTelNumber = callerPrincipal.getName();

            User user = userManager.getUser(callerTelNumber);
            String dataToSign = user.getId().toString() + user.getTelNumber() + user.getVersion().toString();

            String signature = entityTagSignatureProvider.sign(dataToSign);

            return Response.ok().entity(user).tag(new EntityTag(signature)).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @EtagValidatorFilterBinding
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response editSelf(@Valid ClientEditDto clientEditDto) {
        try {
            String dataToSign = clientEditDto.id + clientEditDto.telNumber + clientEditDto.version;
            if (!entityTagSignatureProvider.verifyIntegrity(dataToSign)) {
                return Response.status(Response.Status.PRECONDITION_FAILED).build();
            }

            userManager.edit(UUID.fromString(clientEditDto.getId()), clientEditDto);
            
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
