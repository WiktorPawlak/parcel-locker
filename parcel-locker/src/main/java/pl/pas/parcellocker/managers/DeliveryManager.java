package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.DeliveryRepository;
import pl.pas.parcellocker.repositories.LockerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;

public class DeliveryManager {

    private final DeliveryRepository deliveries;
    private final LockerRepository lockers;

    public DeliveryManager() {
        deliveries = new DeliveryRepository();
        lockers = new LockerRepository();
    }

    public Delivery makeParcelDelivery(
        BigDecimal basePrice,
        double width,
        double height,
        double length,
        double weight,
        boolean isFragile,
        Client shipper,
        Client receiver,
        Locker locker
    ) {
        validateClient(shipper);
        validateClient(receiver);

        Delivery delivery = new Delivery(basePrice, width, height, length, weight, isFragile, shipper, receiver, locker);
        deliveries.add(delivery);
        return delivery;
    }

    public Delivery makeListDelivery(
        BigDecimal basePrice,
        boolean isPriority,
        Client shipper,
        Client receiver,
        Locker locker
    ) {
        validateClient(shipper);
        validateClient(receiver);

        Delivery delivery = new Delivery(basePrice, isPriority, shipper, receiver, locker);
        deliveries.add(delivery);
        return delivery;
    }

    public void putInLocker(Delivery delivery, String accessCode) {
        validateClient(delivery.getReceiver());
        validateClient(delivery.getShipper());

        delivery = deliveries.get(delivery.getId());
        validateDelivery(delivery);

        Delivery latestDeliveryState = deliveries.get(delivery.getId());
        Locker chosenLocker = latestDeliveryState.getLocker();

        chosenLocker.putIn(latestDeliveryState, latestDeliveryState.getReceiver().getTelNumber(), accessCode);

        latestDeliveryState.setAllocationStart(LocalDateTime.now());
        latestDeliveryState.setStatus(READY_TO_PICKUP);

        deliveries.update(latestDeliveryState);
    }

    public void takeOutDelivery(Locker locker, Client receiver, String accessCode) {
        validateClient(receiver);

        Locker locker1 = lockers.get(locker.getId());
        Delivery delivery = locker1.takeOut(receiver.getTelNumber(), accessCode);

        if (delivery != null) {
            delivery.setAllocationStop(LocalDateTime.now());
            delivery.setStatus(DeliveryStatus.RECEIVED);
            delivery.setArchived(true);

            deliveries.update(delivery);

        }
    }

    public BigDecimal checkClientShipmentBalance(Client client) {
        BigDecimal balance = BigDecimal.ZERO;
        if (client == null)
            throw new DeliveryManagerException("client is a nullptr!");
        for (Delivery delivery : deliveries.findAll()) {
            if (delivery.getShipper().equals(client))
                balance = balance.add(delivery.getCost());
        }

        return balance;
    }

    public List<Delivery> getAllClientDeliveries(Client client) {
        return deliveries.findBy(delivery -> delivery.getReceiver().equals(client));
    }

    public List<Delivery> getAllReceivedClientDeliveries(Client client) {
        return deliveries.findBy(delivery -> delivery.getReceiver().equals(client) && delivery.isArchived());
    }

    private void validateClient(Client client) {
        if (!client.isActive())
            throw new DeliveryManagerException("Client account is inactive.");
    }

    private void validateDelivery(Delivery delivery) {
        if (delivery.getStatus() == READY_TO_PICKUP || delivery.isArchived())
            throw new DeliveryManagerException("Delivery is already in locker or is archived.");
    }
}
