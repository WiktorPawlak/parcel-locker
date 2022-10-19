package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.model.Locker;

import java.util.UUID;

@Slf4j
public class DeliveryRepository extends Repository<Delivery> {

    public DeliveryRepository() {
        super(Delivery.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            Delivery delivery = entityManager.find(Delivery.class, id);
            delivery.setStatus(DeliveryStatus.RECEIVED);
            delivery.setArchived(true);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }


}
