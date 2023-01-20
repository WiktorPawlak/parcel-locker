package pl.pas.parcellocker.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockerDto {
    @NotEmpty
    public String identityNumber;
    @NotEmpty
    public String address;
    public int numberOfBoxes;
}
