package pl.pas.parcellocker.model.delivery;

import java.util.List;
import java.util.UUID;

import pl.pas.parcellocker.model.user.User;

public interface DeliveryRepository {
    Delivery get(UUID deliveryId);

    void add(Delivery delivery);

    void update(Delivery delivery);

    void remove(Delivery delivery);

    List<Delivery> findAll();

    List<Delivery> findReceivedByClient(User user);

    List<Delivery> findCurrentByClient(User user);

    List<Delivery> findByUser(User user);

}
