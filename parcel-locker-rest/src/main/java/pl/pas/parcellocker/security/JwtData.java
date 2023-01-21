package pl.pas.parcellocker.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtData {
    private String telNumber;
    private List<String> roles;
}
