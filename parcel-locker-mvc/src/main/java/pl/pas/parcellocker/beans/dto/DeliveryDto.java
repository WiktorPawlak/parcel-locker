package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeliveryDto {
    @NotEmpty
    public String shipperTel;
    @NotEmpty public String receiverTel;
    @NotEmpty public String lockerId;
}
