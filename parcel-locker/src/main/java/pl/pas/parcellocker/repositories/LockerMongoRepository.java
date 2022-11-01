package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class LockerMongoRepository extends AbstractMongoRepository<Locker> {
    public LockerMongoRepository() {
        super("lockers", Locker.class);
    }

    public void update(Locker locker) {
        MongoCollection<Locker> lockerCollection = parcelLocker.getCollection(collectionName, Locker.class);
        Bson filter = Filters.eq("_id", locker.getId());

        Bson setUpdate = Updates.combine(
            Updates.set("identityNumber", locker.getIdentityNumber()),
            Updates.set("address", locker.getAddress()),
            Updates.set("depositBoxes", locker.getDepositBoxes())
            );

        lockerCollection.updateOne(filter, setUpdate);
    }

    public List<Locker> findByIdentityNumber(String identityNumber) {
        MongoCollection<Locker> collection = parcelLocker.getCollection(collectionName, Locker.class);
        Bson filter = Filters.eq("identityNumber", identityNumber);
        return collection.find().filter(filter).into(new ArrayList<>());
    }
}
