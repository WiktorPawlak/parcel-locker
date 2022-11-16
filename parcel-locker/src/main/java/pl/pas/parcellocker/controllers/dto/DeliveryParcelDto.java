package pl.pas.parcellocker.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryParcelDto {
    public String shipperTel;
    public String receiverTel;
    public ParcelDto pack;
    public String lockerId;
}
