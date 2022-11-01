package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
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
