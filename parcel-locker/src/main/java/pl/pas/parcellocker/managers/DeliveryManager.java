package pl.pas.parcellocker.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.DeliveryRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;
import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;

public class DeliveryManager {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryManager.class);

    private final DeliveryRepository currentDeliveries;
    private final DeliveryRepository archivedDeliveries;

    public DeliveryManager() {
        currentDeliveries = new DeliveryRepository();
        archivedDeliveries = new DeliveryRepository();
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
        currentDeliveries.add(delivery);
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
        currentDeliveries.add(delivery);
        return delivery;
    }

    public void putInLocker(Delivery delivery, String accessCode) {
        delivery.getLocker().putIn(delivery.getId(), delivery.getReceiver().getTelNumber(), accessCode);
        delivery.setStatus(READY_TO_PICKUP);
        //TODO update
    }

    public boolean takeOutDelivery(Locker locker, Client receiver, String accessCode) {
        UUID deliveryId = locker.takeOut(receiver.getTelNumber(), accessCode);

        Delivery delivery;
        try {
            delivery = currentDeliveries.findById(deliveryId);
        } catch (NotFoundException exception) {
            logger.error(exception.getMessage());
            return false;
        }

        currentDeliveries.remove(delivery);
        archivedDeliveries.add(delivery);
        delivery.setStatus(RECEIVED);
        return true;
    }

    public BigDecimal checkClientShipmentBalance(Client client) {
        BigDecimal balance = BigDecimal.ZERO;
        if (client == null)
            throw new DeliveryManagerException("client is a nullptr!");
        for (Delivery delivery : currentDeliveries.findAll()) {
            if (delivery.getShipper() == client)
                balance = balance.add(delivery.getCost());
        }

        return balance;
    }

    public List<Delivery> getAllClientDeliveries(Client client) {
        return getChosenDeliveriesForClient(currentDeliveries, client);
    }

    public List<Delivery> getAllReceivedClientDeliveries(Client client) {
        return getChosenDeliveriesForClient(archivedDeliveries, client);
    }

    private List<Delivery> getChosenDeliveriesForClient(DeliveryRepository repository, Client client) {
        if (client == null)
            throw new DeliveryManagerException("client is a nullptr!");

        return repository.findBy(delivery -> {
            Client receiver = delivery.getReceiver();
            String telNumber = receiver.getTelNumber();
            return telNumber.equals(client.getTelNumber());
        });
    }

    public DeliveryRepository getArchivedDeliveries() {
        return archivedDeliveries;
    }
}
