package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Locker;

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
}
