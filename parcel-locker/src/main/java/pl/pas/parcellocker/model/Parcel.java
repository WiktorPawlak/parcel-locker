package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.exceptions.ParcelException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static pl.pas.parcellocker.configuration.ParcelConfig.LARGE_PACKAGE_MULTIPLAYER;
import static pl.pas.parcellocker.configuration.ParcelConfig.LARGE_SIZE;
import static pl.pas.parcellocker.configuration.ParcelConfig.MAX_PARCEL_SIZE;
import static pl.pas.parcellocker.configuration.ParcelConfig.MAX_PARCEL_WEIGHT;
import static pl.pas.parcellocker.configuration.ParcelConfig.MEDIUM_PACKAGE_MULTIPLAYER;
import static pl.pas.parcellocker.configuration.ParcelConfig.MEDIUM_SIZE;
import static pl.pas.parcellocker.configuration.ParcelConfig.MIN_PARCEL_SIZE;
import static pl.pas.parcellocker.configuration.ParcelConfig.MIN_PARCEL_WEIGHT;
import static pl.pas.parcellocker.configuration.ParcelConfig.SMALL_PACKAGE_MULTIPLAYER;

@Getter
@Setter
@EqualsAndHashCode
public class Parcel extends Package {

    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean fragile;

    public Parcel(BigDecimal basePrice,
                  double width,
                  double length,
                  double height,
                  double weight,
                  boolean fragile) {
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
