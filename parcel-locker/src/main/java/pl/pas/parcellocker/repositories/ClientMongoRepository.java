package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;

import java.util.UUID;

public class ClientMongoRepository extends AbstractMongoRepository<Client> {
    public ClientMongoRepository() {
        super("clients", Client.class);
    }

    public void update(Client client) {
        MongoCollection<Client> clientsCollection = parcelLocker.getCollection(collectionName, Client.class);
        Bson filter = Filters.eq("_id", client.getId());

        Bson setUpdate = Updates.combine(
            Updates.set("firstname", client.getFirstName()),
            Updates.set("lastname", client.getLastName()),
            Updates.set("telnumber", client.getTelNumber()),
            Updates.set("active", client.isActive())
        );

        clientsCollection.updateOne(filter, setUpdate);
    }

    public Client findByTelNumber(String telNumber) {
        MongoCollection<Client> collection = parcelLocker.getCollection(collectionName, Client.class);
        Bson filter = Filters.eq("telnumber", telNumber);
        return collection.find().filter(filter).first();
    }
}
