package pl.pas.parcellocker.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@BsonDiscriminator(key = "_clazz", value = "package")
public abstract class Package extends MongoEntityModel {

    public BigDecimal basePrice;

    public Package(@BsonProperty("_id") UniqueId id,@BsonProperty("basePrice") BigDecimal basePrice) {
        super(id);
        this.basePrice = basePrice;
    }

    public Package(BigDecimal basePrice) {
        super(new UniqueId());
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
