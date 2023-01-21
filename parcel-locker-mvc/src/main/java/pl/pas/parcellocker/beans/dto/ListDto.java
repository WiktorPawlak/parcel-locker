package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListDto {
  @NotNull public BigDecimal basePrice;
  public boolean isPriority;
}
