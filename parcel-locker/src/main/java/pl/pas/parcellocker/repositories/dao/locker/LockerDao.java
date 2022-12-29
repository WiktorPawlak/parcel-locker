package pl.pas.parcellocker.repositories.dao.locker;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import pl.pas.parcellocker.model.Locker;

import java.util.UUID;

@Dao
public interface LockerDao {

    @Select
    PagingIterable<Locker> all();

    @Insert
    void create(Locker locker);

    @Select
    Locker findByIdentityNumber(String identityNumber);

    @Update
    void update(Locker locker, @CqlName("identity_number") String identityNumber);

    @Delete
    void delete(Locker locker);

}
