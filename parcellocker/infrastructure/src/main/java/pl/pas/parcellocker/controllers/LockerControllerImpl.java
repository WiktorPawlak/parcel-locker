package pl.pas.parcellocker.controllers;

import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.LockerController;
import pl.pas.parcellocker.api.dto.LockerDto;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.managers.LockerManager;
import pl.pas.parcellocker.model.locker.Locker;

@Path(value = "/lockers")
public class LockerControllerImpl implements LockerController {

    @Inject
    private LockerManager lockerManager;

    public Response getLocker(String identityNumber) {
        try {
            return Response.ok().entity(lockerManager.getLocker(identityNumber)).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response addLocker(LockerDto lockerDto) {
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

    public Response removeLocker(String identityNumber) {
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
