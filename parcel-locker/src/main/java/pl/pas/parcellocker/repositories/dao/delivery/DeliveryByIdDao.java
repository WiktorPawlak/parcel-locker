package pl.pas.parcellocker.repositories.dao.delivery;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import pl.pas.parcellocker.model.Delivery;

import java.util.UUID;

@Dao
public interface DeliveryByIdDao {

    @Select
    PagingIterable<Delivery> all();

    @Insert
    void create(Delivery delivery);

    @Select
    Delivery findById(UUID id);

    @Update
    void update(Delivery client, @CqlName("entity_id") UUID entity_id);

    @Delete
    void delete(Delivery client);
}
