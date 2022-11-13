package pl.pas.parcellocker.controllers.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement
public class ClientDto {
    public String firstName;
    public String lastName;
    public String telNumber;
}
