package pl.pas.parcellocker.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryListDto {
  public String shipperTel;
  public String receiverTel;
  public ListDto pack;
  public String lockerId;
}
