package pl.pas.parcellocker.repositories.mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByClientDao;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByIdDao;

@Mapper
public interface DeliveryMapper {

    @DaoFactory
    DeliveryByIdDao deliveryByIdDao();

    @DaoFactory
    DeliveryByClientDao deliveryByClientDao();

    static MapperBuilder<DeliveryMapper> builder(CqlSession session) {
        return new DeliveryMapperBuilder(session);
    }
}
