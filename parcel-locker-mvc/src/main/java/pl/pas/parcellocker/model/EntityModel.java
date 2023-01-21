package pl.pas.parcellocker.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class EntityModel implements Serializable {

    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
