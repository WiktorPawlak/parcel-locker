package pl.pas.parcellocker.model;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

    private UUID entityId;

    public AbstractEntity(UUID entityId) {
        this.entityId = entityId;
    }

    public UUID getEntityId() {
        return entityId;
    }
}
