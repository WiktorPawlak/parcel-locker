package pl.pas.parcellocker.beans.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    private UUID id;
    private Long version;
    private String telNumber;
    private String firstName;
    private String lastName;
    private String password;
    private boolean admin;
    private boolean moderator;
    private boolean active;
}
