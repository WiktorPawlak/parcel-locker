package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParcelDto {
  @NotNull public BigDecimal basePrice;
  public double width;
  public double height;
  public double length;
  public double weight;
  public boolean isFragile;
}
