package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeliveryMongoRepository extends AbstractMongoRepository<Delivery> {
    public DeliveryMongoRepository() {
        super("deliveries", Delivery.class);
    }

    public void update(Delivery delivery) {
        MongoCollection<Delivery> deliveryCollection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.eq("_id", delivery.getId());

        Bson setUpdate = Updates.combine(
            Updates.set("shipper", delivery.getShipper()),
            Updates.set("lastname", delivery.getReceiver()),
            Updates.set("status", delivery.getStatus()),
            Updates.set("pack", delivery.getPack()),
            Updates.set("locker", delivery.getLocker()),
            Updates.set("archived", delivery.isArchived())
        );

        deliveryCollection.updateOne(filter, setUpdate);
    }

    public List<Delivery> findByClient(Client client) {
        MongoCollection<Delivery> collection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.eq("receiver._id", client.getId());
        return collection.find().filter(filter).into(new ArrayList<>());
    }

    public List<Delivery> findArchivedByClient(Client client) {
        MongoCollection<Delivery> collection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.and(
            Filters.eq("receiver._id", client.getId()),
            Filters.eq("status", DeliveryStatus.RECEIVED)
        );
        return collection.find().filter(filter).into(new ArrayList<>());
    }
}
