package pl.pas.parcellocker.repositories.dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.provider.ClientGetByIdProvider;

import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    void create(Client client);

    @Select
    Client findById(UUID id);

//    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
//    @QueryProvider(providerClass = ClientGetByIdProvider.class, entityHelpers = {Client.class})
//    Client findByUid(UUID uid);
}
