package pl.pas.parcellocker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
public class MongoEntityModel implements Serializable {

    @BsonProperty("_id")
    @BsonId
    private UniqueId entityId;

    public MongoEntityModel(UniqueId id) {
        this.entityId = id;
    }

    @JsonIgnore
    public UniqueId getEntityId() {
        return entityId;
    }

    public UUID getId() {
        return entityId.getUUID();
    }
}
