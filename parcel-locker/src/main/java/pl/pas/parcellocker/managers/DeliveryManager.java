package pl.pas.parcellocker.managers;

import static pl.pas.parcellocker.model.delivery.DeliveryStatus.READY_TO_PICKUP;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryRepository;
import pl.pas.parcellocker.model.delivery.DeliveryStatus;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;
import pl.pas.parcellocker.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

public class DeliveryManager {

    private final DeliveryRepository deliveryRepository;
    private final LockerRepository lockerRepository;

    public DeliveryManager(
        DeliveryRepository deliveryRepository,
        LockerRepository lockerRepository
    ) {
        this.deliveryRepository = deliveryRepository;
        this.lockerRepository = lockerRepository;
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
        deliveryRepository.add(delivery);
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
        deliveryRepository.add(delivery);
        return delivery;
    }

    public void putInLocker(Delivery delivery, String accessCode) {
        Delivery latestDeliveryState = deliveryRepository.get(delivery.getId());

        validateClient(latestDeliveryState.getReceiver());
        validateClient(latestDeliveryState.getShipper());
        validateDelivery(latestDeliveryState);

        Locker chosenLocker = latestDeliveryState.getLocker();

        chosenLocker.putIn(latestDeliveryState, latestDeliveryState.getReceiver().getTelNumber(), accessCode);

        latestDeliveryState.setAllocationStart(LocalDateTime.now());
        latestDeliveryState.setStatus(READY_TO_PICKUP);

        deliveryRepository.update(latestDeliveryState);
    }

    public void takeOutDelivery(Locker locker, Client receiver, String accessCode) {
        validateClient(receiver);

        Locker locker1 = lockerRepository.get(locker.getId());
        Delivery delivery = locker1.takeOut(receiver.getTelNumber(), accessCode);

        if (delivery != null) {
            delivery.setAllocationStop(LocalDateTime.now());
            delivery.setStatus(DeliveryStatus.RECEIVED);
            delivery.setArchived(true);

            deliveryRepository.update(delivery);

        }
    }

    public BigDecimal checkClientShipmentBalance(Client client) {
        BigDecimal balance = BigDecimal.ZERO;
        if (client == null)
            throw new DeliveryManagerException("client is a nullptr!");
        for (Delivery delivery : deliveryRepository.findAll()) {
            if (delivery.getShipper().equals(client))
                balance = balance.add(delivery.getCost());
        }

        return balance;
    }

    public List<Delivery> getAllClientDeliveries(Client client) {
        return deliveryRepository.findByClient(client);
    }

    public List<Delivery> getAllReceivedClientDeliveries(Client client) {
        return deliveryRepository.findReceivedByClient(client);
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
