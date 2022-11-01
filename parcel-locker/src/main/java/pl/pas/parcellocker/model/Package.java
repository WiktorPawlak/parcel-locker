package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@BsonDiscriminator(key = "_clazz")
public abstract class Package extends MongoEntityModel {

    public BigDecimal basePrice;

    @BsonCreator
    public Package(
        @BsonProperty("_id") UniqueId id,
        @BsonProperty("basePrice") BigDecimal basePrice) {
        super(id);
        this.basePrice = basePrice;
    }

    public Package(BigDecimal basePrice) {
        super(new UniqueId());
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
