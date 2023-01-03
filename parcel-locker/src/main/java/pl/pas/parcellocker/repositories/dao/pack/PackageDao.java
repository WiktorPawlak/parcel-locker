package pl.pas.parcellocker.repositories.dao.pack;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import java.util.UUID;

public interface PackageDao<T> {

    @Select
    PagingIterable<T> all();

    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(T pack);

    @Select
    T findById(UUID id);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(T pack,@CqlName("entity_id") UUID entity_id);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(T pack);

}
