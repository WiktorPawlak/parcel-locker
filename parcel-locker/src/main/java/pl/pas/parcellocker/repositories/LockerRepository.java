package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import pl.pas.parcellocker.model.Locker;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;
import static pl.pas.parcellocker.configuration.SchemaNames.ADDRESS;
import static pl.pas.parcellocker.configuration.SchemaNames.DEPOSIT_BOXES;
import static pl.pas.parcellocker.configuration.SchemaNames.ENTITY_ID;
import static pl.pas.parcellocker.configuration.SchemaNames.IDENTITY_NUMBER;

public class LockerRepository extends SessionConnector {

    public void save(Locker locker) {
        Insert insertIntoLockersById = insertInto(PARCEL_LOCKER_NAMESPACE, "lockers_by_id")
            .value(ENTITY_ID, literal(locker.getEntityId()))
            .value(IDENTITY_NUMBER, literal(locker.getIdentityNumber()))
            .value(ADDRESS, literal(locker.getAddress()))
            .value(DEPOSIT_BOXES, literal(locker.getDepositBoxes().get(0), depositBoxCodec));

        session.execute(insertIntoLockersById.build());
    }

}
