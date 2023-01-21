package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClientDto {
  @NotEmpty public String firstName;
  @NotEmpty public String lastName;
  @NotEmpty public String telNumber;
}
