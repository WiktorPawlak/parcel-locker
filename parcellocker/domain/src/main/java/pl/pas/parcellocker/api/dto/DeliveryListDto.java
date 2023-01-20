package pl.pas.parcellocker.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryListDto {
    @NotEmpty
    public String shipperTel;
    @NotEmpty
    public String receiverTel;
    @Valid
    public ListDto pack;
    @NotEmpty
    public String lockerId;
}
