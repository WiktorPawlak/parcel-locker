package pl.pas.parcellocker.repositories;

import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Delivery;

import java.util.UUID;

public class DeliveryRepository extends Repository<Delivery> {
    public Delivery findById(UUID id) {
        return objects.stream()
            .filter(delivery -> delivery.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Delivery Not Found"));
    }
}
