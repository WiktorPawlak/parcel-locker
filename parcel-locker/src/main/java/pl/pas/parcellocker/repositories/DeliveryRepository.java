package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.exceptions.RepositoryException;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static pl.pas.parcellocker.repositories.EntityManagerUtil.getEntityManager;

public class DeliveryRepository extends Repository<Delivery> {

    public DeliveryRepository() {
        super(Delivery.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            Delivery delivery = entityManager.find(Delivery.class, id);
            delivery.setArchived(true);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }


}
