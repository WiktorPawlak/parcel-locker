package pl.pas.parcellocker.repositories.dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import pl.pas.parcellocker.model.Client;

import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    void create(Client client);

    @Select
    Client findById(UUID id);

//    @QueryProvider(providerClass = FindClientQueryProvider.class, entityHelpers = {Client.class})
//    Client findClient(UUID id);
}
