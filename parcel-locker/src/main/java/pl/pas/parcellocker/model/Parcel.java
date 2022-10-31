package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.pas.parcellocker.exceptions.ParcelException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static pl.pas.parcellocker.configuration.ParcelConfig.*;

@BsonDiscriminator(key = "_clazz", value = "parcel")
@Getter
@Setter
public class Parcel extends Package {

    @BsonProperty("width")
    private double width;
    @BsonProperty("length")
    private double length;
    @BsonProperty("height")
    private double height;
    @BsonProperty("weight")
    private double weight;
    @BsonProperty("fragile")
    private boolean fragile;

    @BsonCreator
    public Parcel(@BsonProperty("basePrice")BigDecimal basePrice,
                  @BsonProperty("width") double width,
                  @BsonProperty("length") double length,
                  @BsonProperty("height") double height,
                  @BsonProperty("weight") double weight,
                  @BsonProperty("fragile") boolean fragile) {
        super(basePrice);

        validateSize(width);
        validateSize(length);
        validateSize(height);
        validateWeight(weight);

        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.fragile = fragile;
    }

    private void validateSize(double size) {
        if (size <= MIN_PARCEL_SIZE || size > MAX_PARCEL_SIZE)
            throw new ParcelException("invalid size value!");
    }

    private void validateWeight(double weight) {
        if (weight <= MIN_PARCEL_WEIGHT || weight > MAX_PARCEL_WEIGHT)
            throw new ParcelException("invalid weight value!");
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice;

        ParcelType packageType = checkParcelType();
        switch (packageType) {
            case SMALL:
                cost = cost.multiply(SMALL_PACKAGE_MULTIPLAYER);
                break;
            case MEDIUM:
                cost = cost.multiply(MEDIUM_PACKAGE_MULTIPLAYER);
                break;
            case LARGE:
                cost = cost.multiply(LARGE_PACKAGE_MULTIPLAYER);
                break;
            default:
                break;
        }

        return cost;
    }

    private ParcelType checkParcelType() {
        List<Double> dims = Arrays.asList(width, length, height);
        ParcelType type;

        if (dims.stream().anyMatch(dim -> dim >= LARGE_SIZE)) {
            type = ParcelType.LARGE;
        } else if (dims.stream().anyMatch(dim -> dim >= MEDIUM_SIZE)) {
            type = ParcelType.MEDIUM;
        } else {
            type = ParcelType.SMALL;
        }

        return type;
    }

    private enum ParcelType {SMALL, MEDIUM, LARGE}
}
