package pl.pas.parcellocker.repositories.dao.client;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import pl.pas.parcellocker.model.Client;

import java.util.UUID;

@Dao
public interface ClientDao {

    @Select
    PagingIterable<Client> all();

    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(Client client);

    @Select
    Client findById(UUID id);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(Client client, @CqlName("entity_id") UUID entity_id);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(Client client);

}
