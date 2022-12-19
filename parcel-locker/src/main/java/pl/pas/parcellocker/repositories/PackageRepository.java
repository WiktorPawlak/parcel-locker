package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import pl.pas.parcellocker.configuration.SchemaConst;
import pl.pas.parcellocker.model.Package;
import pl.pas.parcellocker.model.Parcel;
import pl.pas.parcellocker.repositories.dao.pack.PackageDao;
import pl.pas.parcellocker.repositories.mapper.PackageMapper;

import java.util.List;
import java.util.UUID;

public class PackageRepository extends SessionConnector {

    private final PackageDao listDao;
    private final PackageDao parcelDao;

    public PackageRepository() {
        PackageMapper packageMapper = initPackageMapper(session);
        this.parcelDao = packageMapper.parcelDao();
        this.listDao = packageMapper.listDao();
    }

    private PackageMapper initPackageMapper(CqlSession session) {
        return PackageMapper.builder(session)
            .withDefaultKeyspace(SchemaConst.PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    private PackageDao getPackageDao(Package pack) {
        return pack instanceof Parcel ? parcelDao : listDao;
    }


    public void save(Package pack) {
        getPackageDao(pack).create(pack);
    }

    public void update(Package pack) {
        getPackageDao(pack).update(pack, pack.getEntityId());
    }

    public void delete(Package pack) {
        getPackageDao(pack).delete(pack);
    }

    public List findAll() {
    return List.of(parcelDao.all().all(), listDao.all().all());
    }

    public Package findById(UUID id) {
        Package pack = (Package) parcelDao.findById(id);

        return pack != null ? pack : (Package) listDao.findById(id);

    }

}
