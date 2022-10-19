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

            entityManager.find(Delivery.class, id).setStatus(DeliveryStatus.RECEIVED);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }

    public void update(Delivery delivery) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.merge(delivery);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }
}
