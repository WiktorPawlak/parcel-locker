package pl.pas.parcellocker.managers;

import static pl.pas.parcellocker.model.delivery.DeliveryStatus.READY_TO_PICKUP;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryRepository;
import pl.pas.parcellocker.model.delivery.DeliveryStatus;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManager {

    @Inject
    private DeliveryRepository deliveryRepository;
    @Inject
    private LockerRepository lockerRepository;
    @Inject
    private UserRepository userRepository;

    public synchronized Delivery makeParcelDelivery(
        BigDecimal basePrice,
        double width,
        double height,
        double length,
        double weight,
        boolean isFragile,
        String shipperTel,
        String receiverTel,
        String lockerId) {
        Client shipper =
            (Client) userRepository
                .findByTelNumber(shipperTel)
                .orElseThrow();

        Client receiver =
            (Client) userRepository
                .findByTelNumber(receiverTel)
                .orElseThrow();

        Locker locker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        Delivery delivery =
            new Delivery(
                basePrice, width, height, length, weight, isFragile, shipper, receiver, locker);
        deliveryRepository.add(delivery);
        return delivery;
    }

    public synchronized Delivery makeListDelivery(
        BigDecimal basePrice,
        boolean isPriority,
        String shipperTel,
        String receiverTel,
        String lockerId) {
        Client shipper =
            (Client) userRepository
                .findByTelNumber(shipperTel)
                .orElseThrow();

        Client receiver =
            (Client) userRepository
                .findByTelNumber(receiverTel)
                .orElseThrow();

        Locker locker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        Delivery delivery = new Delivery(basePrice, isPriority, shipper, receiver, locker);
        deliveryRepository.add(delivery);
        return delivery;
    }

    public synchronized void putInLocker(UUID deliveryId, String lockerId, String accessCode) {
        Delivery latestDeliveryState = deliveryRepository.get(deliveryId);

        validateClient(latestDeliveryState.getReceiver());
        validateClient(latestDeliveryState.getShipper());
        validateDelivery(latestDeliveryState);

        Locker chosenLocker =
            lockerRepository
                .findByIdentityNumber(lockerId)
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));

        chosenLocker.putIn(
            latestDeliveryState, latestDeliveryState.getReceiver().getTelNumber(), accessCode);

        latestDeliveryState.setAllocationStart(LocalDateTime.now());
        latestDeliveryState.setStatus(READY_TO_PICKUP);
        lockerRepository.update(chosenLocker);
        deliveryRepository.update(latestDeliveryState);
    }

    public synchronized void takeOutDelivery(UUID deliveryId, String receiverTel, String accessCode) {
        Delivery latestDeliveryState = deliveryRepository.get(deliveryId);

        Locker locker =
            lockerRepository
                .findByIdentityNumber(latestDeliveryState.getLocker().getIdentityNumber())
                .orElseThrow(() -> new DeliveryManagerException("Locker not found"));
        ;
        Delivery delivery = locker.takeOut(receiverTel, accessCode);

        if (delivery != null) {
            delivery.setAllocationStop(LocalDateTime.now());
            delivery.setStatus(DeliveryStatus.RECEIVED);
            delivery.setArchived(true);

            deliveryRepository.update(delivery);

        }
    }

    public BigDecimal checkClientShipmentBalance(User user) {
        BigDecimal balance = BigDecimal.ZERO;
        if (user == null) throw new DeliveryManagerException("User is a nullptr!");
        for (Delivery delivery : deliveryRepository.findAll()) {
            if (delivery.getShipper().equals(user)) balance = balance.add(delivery.getCost());
        }

        return balance;
    }

    public List<Delivery> getAllClientDeliveries(User user) {
        return deliveryRepository.findByUser(user);
    }

    public List<Delivery> getAllCurrentClientDeliveries(String telNumber) {
        Client client =
            (Client) userRepository
                .findByTelNumber(telNumber)
                .orElseThrow(() -> new DeliveryManagerException("Client not found"));

        return deliveryRepository.findCurrentByClient(client);
    }

    public List<Delivery> getAllReceivedClientDeliveries(String telNumber) {
        Client receiver =
            (Client) userRepository
                .findByTelNumber(telNumber)
                .orElseThrow(() -> new DeliveryManagerException("Receiver not found"));

        return deliveryRepository.findReceivedByClient(receiver);
    }

    public Delivery getDelivery(UUID id) {
        return deliveryRepository.get(id);
    }

    private void validateClient(User user) {
        if (!user.isActive()) throw new DeliveryManagerException("User account is inactive.");
    }

    private void validateDelivery(Delivery delivery) {
        if (delivery.getStatus() == READY_TO_PICKUP || delivery.isArchived())
            throw new DeliveryManagerException("Delivery is already in locker or is archived.");
    }
}
