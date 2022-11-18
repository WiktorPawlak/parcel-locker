package pl.pas.parcellocker.controllers;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.LockerException;
import pl.pas.parcellocker.managers.DeliveryManager;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping(value = "/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryManager deliveryManager;

    @GetMapping("/{id}")
    public ResponseEntity getDelivery(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok().body(deliveryManager.getDelivery(id));
        } catch (NoResultException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping("/current")
  public ResponseEntity getCurrentDeliveries(@RequestParam("telNumber") String telNumber) {
    try {
      return ResponseEntity.ok().body(deliveryManager.getAllCurrentClientDeliveries(telNumber));
    } catch (NoResultException e) {
      return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
  }

    @GetMapping("/received")
  public ResponseEntity getReceivedDelivery(@RequestParam("telNumber") String telNumber) {
    try {
      return ResponseEntity.ok()
          .body(deliveryManager.getAllReceivedClientDeliveries(telNumber))
          ;
    } catch (NoResultException | DeliveryManagerException e) {
      return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
  }

    @PostMapping("/list")
    public ResponseEntity addListDelivery(@Valid DeliveryListDto deliveryListDto) {
        try {
            Delivery delivery =
                deliveryManager.makeListDelivery(
                    deliveryListDto.pack.basePrice,
                    deliveryListDto.pack.isPriority,
                    deliveryListDto.shipperTel,
                    deliveryListDto.receiverTel,
                    deliveryListDto.lockerId);
            return ResponseEntity.status(CREATED).body(delivery);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/parcel")
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
            return ResponseEntity.status(CREATED).body(delivery);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/put-in")
  public ResponseEntity putInLocker(
      @RequestParam("id") UUID deliveryId,
      @RequestParam("lockerId") String lockerId,
      @RequestParam("accessCode") String accessCode) {
    try {
      deliveryManager.putInLocker(deliveryId, lockerId, accessCode);
      return ResponseEntity.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return ResponseEntity.status(NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    } catch (LockerException e) {
      return ResponseEntity.status(CONFLICT).body(e.getMessage());
    }
  }

  @PutMapping("/{id}/take-out")
  public ResponseEntity takeOutDelivery(
      @RequestParam("id") UUID deliveryId,
      @RequestParam("telNumber") String telNumber,
      @RequestParam("accessCode") String accessCode) {
    try {
      deliveryManager.takeOutDelivery(deliveryId, telNumber, accessCode);
      return ResponseEntity.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return ResponseEntity.status(NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    } catch (LockerException e) {
      return ResponseEntity.status(CONFLICT).body(e.getMessage());
    }
  }
}
