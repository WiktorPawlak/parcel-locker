//package pl.pas.parcellocker.repositories;
//
//import com.datastax.oss.driver.api.mapper.annotations.Dao;
//import com.datastax.oss.driver.api.mapper.annotations.Insert;
//import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
//import pl.pas.parcellocker.model.Client;
//
//import java.util.UUID;
//
//@Dao
//public interface ClientDao {
//
//    @Insert
//    void create(Client client);
//
//    @QueryProvider(providerClass = FindClientQueryProvider.class, entityHelpers = {Client.class})
//    Client findClient(UUID id);
//}
