package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor
public class UniqueId {

    private UUID id;

    UniqueId(UUID id) {
        this.id = id;
    }

    public UUID getUUID() {
        return id;
    }
}
