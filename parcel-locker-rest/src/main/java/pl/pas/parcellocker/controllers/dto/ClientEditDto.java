package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClientEditDto {

    @NotEmpty
    public String firstName;

    @NotEmpty
    public String lastName;

    public String password;
}
