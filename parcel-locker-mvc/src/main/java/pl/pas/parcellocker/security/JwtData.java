package pl.pas.parcellocker.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtData {
    private String telNumber;
    private Set<String> roles;
}
