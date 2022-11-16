package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
  @NotEmpty public BigDecimal basePrice;
  public boolean isPriority;
}
