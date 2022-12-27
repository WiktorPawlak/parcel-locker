package pl.pas.parcellocker.repositories.dao.delivery;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;

import java.util.UUID;

@Dao
public interface DeliveryByClientDao {

    @Query("SELECT entity_id FROM parcel_locker.delivery_by_client WHERE receiver_id = :receiver_id")
    ResultSet findByClientId(@CqlName("receiver_id") UUID receiver_id);

    @Query("SELECT entity_id FROM parcel_locker.delivery_by_client WHERE receiver_id = :receiver_id AND archived = :archived")
    ResultSet findArchivedByClientId(@CqlName("receiver_id") UUID receiver_id, @CqlName("archived") boolean archived);

}
