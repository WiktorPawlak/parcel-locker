package pl.pas.parcellocker.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
  public BigDecimal basePrice;
  public boolean isPriority;
}
