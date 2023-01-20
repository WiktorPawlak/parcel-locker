package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelDto {
  @NotNull public BigDecimal basePrice;
  public double width;
  public double height;
  public double length;
  public double weight;
  public boolean isFragile;
}
