package pl.pas.parcellocker.configuration;

import java.math.BigDecimal;

public class ParcelConfig {
    public static final int SMALL_SIZE = 20;
    public static final int MEDIUM_SIZE = 30;
    public static final int LARGE_SIZE = 40;

    public static final BigDecimal SMALL_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(1.0d);
    public static final BigDecimal MEDIUM_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(1.5d);
    public static final BigDecimal LARGE_PACKAGE_MULTIPLAYER = BigDecimal.valueOf(2.0d);

    public static final int MIN_PARCEL_SIZE = 0;
    public static final int MAX_PARCEL_SIZE = 40;

    public static final int MIN_PARCEL_WEIGHT = 0;
    public static final int MAX_PARCEL_WEIGHT = 20;
}
