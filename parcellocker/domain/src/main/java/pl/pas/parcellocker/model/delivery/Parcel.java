package pl.pas.parcellocker.model.delivery;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.configuration.ParcelConfig;
import pl.pas.parcellocker.exceptions.ParcelException;

@Entity
@NoArgsConstructor
@DiscriminatorColumn(name = "PARCEL")
@Access(AccessType.FIELD)
public class Parcel extends Package {
    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean fragile;

    public Parcel(BigDecimal basePrice, double width, double length, double height, double weight, boolean fragile) {
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
        if (size <= ParcelConfig.MIN_PARCEL_SIZE || size > ParcelConfig.MAX_PARCEL_SIZE)
            throw new ParcelException("invalid size value!");
    }

    private void validateWeight(double weight) {
        if (weight <= ParcelConfig.MIN_PARCEL_WEIGHT || weight > ParcelConfig.MAX_PARCEL_WEIGHT)
            throw new ParcelException("invalid weight value!");
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice;

        ParcelType packageType = checkParcelType();
        switch (packageType) {
            case SMALL:
                cost = cost.multiply(ParcelConfig.SMALL_PACKAGE_MULTIPLAYER);
                break;
            case MEDIUM:
                cost = cost.multiply(ParcelConfig.MEDIUM_PACKAGE_MULTIPLAYER);
                break;
            case LARGE:
                cost = cost.multiply(ParcelConfig.LARGE_PACKAGE_MULTIPLAYER);
                break;
            default:
                break;
        }

        return cost;
    }

    private ParcelType checkParcelType() {
        List<Double> dims = Arrays.asList(width, length, height);
        ParcelType type;

        if (dims.stream().anyMatch(dim -> dim >= ParcelConfig.LARGE_SIZE)) {
            type = ParcelType.LARGE;
        } else if (dims.stream().anyMatch(dim -> dim >= ParcelConfig.MEDIUM_SIZE)) {
            type = ParcelType.MEDIUM;
        } else {
            type = ParcelType.SMALL;
        }

        return type;
    }

    private enum ParcelType {SMALL, MEDIUM, LARGE}
}
