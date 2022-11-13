package pl.pas.parcellocker.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {
    public String firstName;
    public String lastName;
    public String telNumber;
}
