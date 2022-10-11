package pl.pas.parcellocker.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Parcel extends Package {
    private final double width;
    private final double length;
    private final double height;
    private final double weight;
    private final boolean fragile;

    private static final int SMALL_SIZE = 20;
    private static final int MEDIUM_SIZE = 30;
    private static final int LARGE_SIZE = 40;

    private static final BigDecimal SMALL_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(1.0d);
    private static final BigDecimal MEDIUM_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(1.5d);
    private static final BigDecimal LARGE_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(2.0d);

    public Parcel(double width, double length, double height, double weight, boolean fragile, BigDecimal basePrice) {
        super(basePrice);

        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.fragile = fragile;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = new BigDecimal(String.valueOf(basePrice));

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

        if (dims.stream().anyMatch(dim -> dim > LARGE_SIZE)) {
            type = ParcelType.LARGE;
        }
        else if (dims.stream().anyMatch(dim -> dim > MEDIUM_SIZE)) {
            type = ParcelType.MEDIUM;
        }
        else {
            type = ParcelType.SMALL;
        }

        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(width).append("x").append(length).append("x").append(height)
                .append(" ")
                .append(weight).append("kg")
                .append(" cost: ").append(getCost())
                .append(super.toString())
                .toString();
    }

    private enum ParcelType { SMALL, MEDIUM, LARGE }
}
