package pl.pas.parcellocker.model.delivery;

import java.util.List;
import java.util.UUID;

import pl.pas.parcellocker.model.user.User;

public interface DeliveryRepository {
    Delivery get(UUID deliveryId);

    void add(Delivery delivery);

    void update(Delivery delivery);

    List<Delivery> findAll();
    List<Delivery> findReceivedByClient(Client client);
    List<Delivery> findCurrentByClient(Client client);

    List<Delivery> findByUser(User user);

}
