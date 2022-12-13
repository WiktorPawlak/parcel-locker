package pl.pas.parcellocker.repositories.mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import pl.pas.parcellocker.repositories.dao.ClientDao;


@Mapper
public interface ClientMapper {
    @DaoFactory
    ClientDao clientDao();

    static MapperBuilder<ClientMapper> builder(CqlSession session) {
        return new ClientMapperBuilder(session);
    }
}
