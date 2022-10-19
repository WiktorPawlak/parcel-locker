package pl.pas.parcellocker.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.DeliveryRepository;
import pl.pas.parcellocker.repositories.LockerRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;
import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;

public class DeliveryManager {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryManager.class);

    private final DeliveryRepository deliveries;
    private final LockerRepository lockerRepository;

    public DeliveryManager() {
        deliveries = new DeliveryRepository();
        lockerRepository = new LockerRepository();
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
        Delivery delivery = new Delivery(basePrice, isPriority, shipper, receiver, locker);
        deliveries.add(delivery);
        return delivery;
    }

    public void putInLocker(Delivery delivery, String accessCode) {
        Locker locker = delivery.getLocker();
        locker.putIn(delivery, delivery.getReceiver().getTelNumber(), accessCode);
        delivery.setStatus(READY_TO_PICKUP);
        deliveries.update(delivery);
    }

    public boolean takeOutDelivery(Locker locker, Client receiver, String accessCode) {
        UUID deliveryId = locker.takeOut(receiver.getTelNumber(), accessCode);

        if (deliveryId != null) {
            deliveries.archive(deliveryId);
            return true;
        }
        return false;
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
}
