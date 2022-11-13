package pl.pas.parcellocker.repositories.hibernate;

import static pl.pas.parcellocker.repositories.hibernate.EntityManagerUtil.getEntityManager;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.exceptions.RepositoryException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryRepository;

public class DeliveryRepositoryHibernate extends HibernateRepository<Delivery> implements DeliveryRepository {

    public DeliveryRepositoryHibernate() {
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

    public List<Delivery> findByClient(Client client) {
        return findBy(delivery -> delivery.getReceiver().equals(client));
    }

    public List<Delivery> findReceivedByClient(Client client) {
        return findBy(delivery -> delivery.getReceiver().equals(client) && delivery.isArchived());
    }
}
