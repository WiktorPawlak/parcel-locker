package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import pl.pas.parcellocker.model.Client;

@Dao
public interface ClientDao {

    @Insert
    void create(Client client);

}
