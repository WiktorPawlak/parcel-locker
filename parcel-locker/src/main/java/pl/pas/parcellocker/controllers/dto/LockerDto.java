package pl.pas.parcellocker.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockerDto {
    public String identityNumber;
    public String address;
    public int numberOfBoxes;
}
