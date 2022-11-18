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
import jakarta.ws.rs.RequestParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.LockerException;
import pl.pas.parcellocker.managers.DeliveryManager;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.util.UUID;

@RequestMapping(value = "/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryManager deliveryManager;

    @GetMapping("/{id}")
    public ResponseEntity getDelivery(@PathParam("id") UUID id) {
        try {
            return ResponseEntity.ok().entity(deliveryManager.getDelivery(id)).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).build();
        }
    }

  @GetMapping
  @Path("/current")
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity getCurrentDeliveries(@RequestParam("telNumber") String telNumber) {
    try {
      return ResponseEntity.ok().entity(deliveryManager.getAllCurrentClientDeliveries(telNumber)).build();
    } catch (NoResultException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }

  @GetMapping
  @Path("/received")
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity getReceivedDelivery(@RequestParam("telNumber") String telNumber) {
    try {
      return ResponseEntity.ok()
          .entity(deliveryManager.getAllReceivedClientDeliveries(telNumber))
          .build();
    } catch (NoResultException | DeliveryManagerException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list")
    public ResponseEntity addListDelivery(@Valid DeliveryListDto deliveryListDto) {
        try {
            Delivery delivery =
                deliveryManager.makeListDelivery(
                    deliveryListDto.pack.basePrice,
                    deliveryListDto.pack.isPriority,
                    deliveryListDto.shipperTel,
                    deliveryListDto.receiverTel,
                    deliveryListDto.lockerId);
            return ResponseEntity.status(ResponseEntity.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(ResponseEntity.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/parcel")
    public ResponseEntity addParcelDelivery(@Valid DeliveryParcelDto deliveryParcelDto) {
        try {
            Delivery delivery =
                deliveryManager.makeParcelDelivery(
                    deliveryParcelDto.pack.basePrice,
                    deliveryParcelDto.pack.width,
                    deliveryParcelDto.pack.height,
                    deliveryParcelDto.pack.length,
                    deliveryParcelDto.pack.weight,
                    deliveryParcelDto.pack.isFragile,
                    deliveryParcelDto.shipperTel,
                    deliveryParcelDto.receiverTel,
                    deliveryParcelDto.lockerId);
            return ResponseEntity.status(ResponseEntity.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(ResponseEntity.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

  @PutMapping
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}/put-in")
  public ResponseEntity putInLocker(
      @PathParam("id") UUID deliveryId,
      @RequestParam("lockerId") String lockerId,
      @RequestParam("accessCode") String accessCode) {
    try {
      deliveryManager.putInLocker(deliveryId, lockerId, accessCode);
      return ResponseEntity.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
    } catch (LockerException e) {
      return ResponseEntity.status(ResponseEntity.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @PutMapping
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}/take-out")
  public ResponseEntity takeOutDelivery(
      @PathParam("id") UUID deliveryId,
      @RequestParam("telNumber") String telNumber,
      @RequestParam("accessCode") String accessCode) {
    try {
      deliveryManager.takeOutDelivery(deliveryId, telNumber, accessCode);
      return ResponseEntity.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return ResponseEntity.status(ResponseEntity.Status.NOT_FOUND).entity(e.getMessage()).build();
    } catch (LockerException e) {
      return ResponseEntity.status(ResponseEntity.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }
}
