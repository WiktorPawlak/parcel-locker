package pl.pas.parcellocker.repositories.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import pl.pas.parcellocker.model.Client;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {

    @Select
    PagingIterable<Client> all();

    @Insert
    void create(Client client);

    @Select
    Client findById(UUID id);

    @Update
    void update(Client client, UUID entity_id);

    @Delete
    void delete(Client client);

//    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
//    @QueryProvider(providerClass = ClientGetByIdProvider.class, entityHelpers = {Client.class})
//    Client findByUid(UUID uid);
}
