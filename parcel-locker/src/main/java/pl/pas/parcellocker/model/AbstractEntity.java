package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

    @PartitionKey
    private UUID entityId;

    public AbstractEntity(UUID entityId) {
        this.entityId = entityId;
    }

    public UUID getEntityId() {
        return entityId;
    }
}
