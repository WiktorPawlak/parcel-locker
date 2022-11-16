package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelDto {
  @NotEmpty public BigDecimal basePrice;
  public double width;
  public double height;
  public double length;
  public double weight;
  @NotEmpty public boolean isFragile;
}
