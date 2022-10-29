package pl.pas.parcellocker.model;

import java.util.UUID;

public class UniqueId {

    private final UUID id;

    UniqueId(UUID id) {
        this.id = id;
    }

    UniqueId() {
        this.id = UUID.randomUUID();
    }

    public UUID getUUID() {
        return id;
    }
}
