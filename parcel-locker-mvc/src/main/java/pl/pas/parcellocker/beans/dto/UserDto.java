package pl.pas.parcellocker.beans.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
  private UUID id;
  private String firstName;
  private String lastName;
  private String telNumber;
  private boolean admin;
  private boolean moderator;
  private boolean active;
}
