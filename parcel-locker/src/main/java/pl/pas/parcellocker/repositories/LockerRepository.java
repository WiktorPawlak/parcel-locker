package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.dao.locker.LockerDao;
import pl.pas.parcellocker.repositories.mapper.LockerMapper;

import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.truncate;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

public class LockerRepository extends SessionConnector {

    private final LockerDao lockerDao;

    public LockerRepository() {
        LockerMapper lockerMapper = initLockerMapper(session);
        this.lockerDao = lockerMapper.lockerDao();
    }

    private LockerMapper initLockerMapper(CqlSession session) {
        return LockerMapper.builder(session)
            .withDefaultKeyspace(PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    public void save(Locker locker) {
        lockerDao.create(locker);
    }

    public void update(Locker locker) {
        lockerDao.update(locker, locker.getIdentityNumber());
    }

    public void delete(Locker locker) {
        lockerDao.delete(locker);
    }

    public List<Locker> findAll() {
        return lockerDao.all().all();
    }

    public Locker findByIdentityNumber(String identityNumber) {
        return lockerDao.findByIdentityNumber(identityNumber);
    }

    public void clear() {
        Truncate truncateDeliveryById = truncate("parcel_locker", "lockers_by_id");
        session.execute(truncateDeliveryById.build());
    }
}
