package pl.pas.parcellocker.model.delivery;

import pl.pas.parcellocker.model.client.Client;

import java.util.List;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery get(UUID deliveryId);
    void add(Delivery delivery);
    void update(Delivery delivery);
    List<Delivery> findAll();
    List<Delivery> findByClient(Client client);
    List<Delivery> findReceivedByClient(Client client);
}
