package pl.pas.parcellocker.model.delivery;

import lombok.Data;

@Data
public class Parcel extends Package {
    private double width;
    private double length;
    private double height;
    private double weight;
    private boolean fragile;

}
