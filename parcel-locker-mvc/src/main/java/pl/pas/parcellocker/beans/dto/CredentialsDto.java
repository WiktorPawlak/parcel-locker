package pl.pas.parcellocker.beans.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class CredentialsDto {

    @NotBlank @Pattern(regexp = "^\\d{9}$")
    String telNumber;

    @NotBlank
    String password;
}
