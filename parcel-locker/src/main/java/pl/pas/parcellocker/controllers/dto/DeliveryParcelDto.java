package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryParcelDto {
  @NotEmpty public String shipperTel;
  @NotEmpty public String receiverTel;
  @NotEmpty public ParcelDto pack;
  @NotEmpty public String lockerId;
}
