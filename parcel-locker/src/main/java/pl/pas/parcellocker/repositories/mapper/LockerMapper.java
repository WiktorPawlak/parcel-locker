package pl.pas.parcellocker.repositories.mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import pl.pas.parcellocker.repositories.dao.locker.LockerDao;

@Mapper
public interface LockerMapper {

    @DaoFactory
    LockerDao lockerDao();

    static MapperBuilder<LockerMapper> builder(CqlSession session) {
        return new LockerMapperBuilder(session);
    }

}
