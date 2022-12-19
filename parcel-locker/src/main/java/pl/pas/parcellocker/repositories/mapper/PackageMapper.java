package pl.pas.parcellocker.repositories.mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import pl.pas.parcellocker.repositories.dao.pack.ListDao;
import pl.pas.parcellocker.repositories.dao.pack.ParcelDao;

@Mapper
public interface PackageMapper {
    @DaoFactory
    ListDao listDao();

    @DaoFactory
    ParcelDao parcelDao();

    static MapperBuilder<PackageMapper> builder(CqlSession session) {
        return new PackageMapperBuilder(session);
    }
}
