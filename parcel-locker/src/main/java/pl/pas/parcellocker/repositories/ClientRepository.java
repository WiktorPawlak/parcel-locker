//package pl.pas.parcellocker.repositories;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceException;
//import pl.pas.parcellocker.exceptions.RepositoryException;
//import pl.pas.parcellocker.model.Client;
//
//import java.util.UUID;
//
//import static pl.pas.parcellocker.repositories.EntityManagerUtil.getEntityManager;
//
//public class ClientRepository extends Repository<Client> {
//
//    public ClientRepository() {
//        super(Client.class);
//    }
//
//    public void archive(UUID id) {
//        try {
//            EntityManager entityManager = getEntityManager();
//
//            entityManager.getTransaction().begin();
//            entityManager.find(Client.class, id).setActive(false);
//            entityManager.getTransaction().commit();
//
//        } catch (PersistenceException e) {
//            throw new RepositoryException(e);
//        }
//    }
//
//    public Client findByTelNumber(String telNumber) {
//        return (Client) getEntityManager()
//            .createQuery("select c from Client c where c.telNumber = :telNumber")
//            .setParameter("telNumber", telNumber)
//            .getSingleResult();
//    }
//
//}
