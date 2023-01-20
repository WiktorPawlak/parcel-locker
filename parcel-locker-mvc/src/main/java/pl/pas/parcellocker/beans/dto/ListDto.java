package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
  @NotNull public BigDecimal basePrice;
  public boolean isPriority;
}
