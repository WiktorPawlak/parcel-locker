package pl.pas.parcellocker.repositories.dao.delivery;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import pl.pas.parcellocker.model.Delivery;

import java.util.List;
import java.util.UUID;

@Dao
public interface DeliveryByIdDao {

    @Select
    PagingIterable<Delivery> all();

    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(Delivery delivery);

    @Select
    Delivery findById(UUID id);

    @Query("SELECT * FROM parcel_locker.delivery_by_id WHERE entity_id IN :ids")
    PagingIterable<Delivery> findByIds(List<UUID> ids);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(Delivery client, @CqlName("entity_id") UUID entity_id);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(Delivery client);
}
