package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Delivery;

import java.util.List;

public class DeliveryMongoRepository extends AbstractMongoRepository<Delivery> {
    public DeliveryMongoRepository() {
        super("deliveries", Delivery.class);
    }

    public void update(Delivery delivery) {
        MongoCollection<Delivery> clientsCollection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.eq("_id", delivery.getId());

        Bson setUpdate = Updates.combine(
            Updates.set("shipper", delivery.getShipper()),
            Updates.set("lastname", delivery.getReceiver()),
            Updates.set("status", delivery.getStatus()),
            //Updates.set("pack", delivery.getPack()),
            Updates.set("locker", delivery.getLocker()),
            Updates.set("archived", delivery.isArchived())
        );

        clientsCollection.updateOne(filter, setUpdate);
    }
}
