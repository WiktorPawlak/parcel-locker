package pl.pas.parcellocker.controllers;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.controllers.dto.LockerDto;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.managers.LockerManager;
import pl.pas.parcellocker.model.locker.Locker;

@Path(value = "/lockers")
public class LockerController {

    @Inject
    private LockerManager lockerManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLockers() {
        try {
            return Response.ok().entity(lockerManager.getAllLockers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocker(@PathParam("identityNumber") String identityNumber) {
        try {
            return Response.ok().entity(lockerManager.getLocker(identityNumber)).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{identityNumber}/empty")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLockerEmptyBoxesCount(@PathParam("identityNumber") String identityNumber) {
        try {
            return Response.ok().entity(lockerManager.getEmptyBoxesCount(identityNumber)).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLocker(@Valid LockerDto lockerDto) {
        try {
            Locker newLocker = lockerManager.createLocker(
                lockerDto.identityNumber,
                lockerDto.address,
                lockerDto.numberOfBoxes
            );
            return Response.status(Response.Status.CREATED).entity(newLocker).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeLocker(@PathParam("identityNumber") String identityNumber) {
        try {
            lockerManager.removeLocker(identityNumber);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
