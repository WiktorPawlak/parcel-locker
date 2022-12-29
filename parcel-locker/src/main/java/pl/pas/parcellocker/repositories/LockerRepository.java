package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.dao.locker.LockerDao;
import pl.pas.parcellocker.repositories.mapper.LockerMapper;

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



}
