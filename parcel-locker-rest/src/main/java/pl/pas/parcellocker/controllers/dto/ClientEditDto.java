package pl.pas.parcellocker.controllers.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    public String id;

    @NotBlank
    @Pattern(regexp = "^\\d{9}$")
    public String telNumber;

    @NotBlank
    public String version;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @NotBlank
    public String password;
}
