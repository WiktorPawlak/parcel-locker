package pl.pas.parcellocker.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.pas.parcellocker.configuration.ListConfig.ADDITIONAL_COST;
import static pl.pas.parcellocker.configuration.ListConfig.RATIO;

@BsonDiscriminator(key = "_clazz", value = "list")
@Getter
@Setter
public class List extends Package {

    @BsonProperty("priority")
    private boolean priority;

    @BsonCreator
    public List(@BsonProperty("basePrice") BigDecimal basePrice,@BsonProperty("priority") boolean priority) {
        super(basePrice);

        this.priority = priority;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice.divide(RATIO, RoundingMode.FLOOR);
        if (priority) cost = cost.add(ADDITIONAL_COST);
        return cost;
    }
}
