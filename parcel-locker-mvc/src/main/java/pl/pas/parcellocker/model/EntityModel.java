package pl.pas.parcellocker.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class EntityModel implements Serializable {

    protected UUID id;

    protected Long version;

    public UUID getId() {
        return id;
    }
}
