package pl.pas.parcellocker.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@MappedSuperclass
public abstract class EntityModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @BsonProperty("uuid")
    protected UUID id;

    @Version
    protected Long version;

    public UUID getId() {
        return id;
    }
}
