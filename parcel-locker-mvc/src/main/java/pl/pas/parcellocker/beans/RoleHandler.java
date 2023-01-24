package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Named
@SessionScoped
@Getter
@Setter
@NoArgsConstructor
public class RoleHandler implements Serializable {

    private final List<String> LOGGED_ROLES = List.of("ADMINISTRATOR", "MODERATOR", "CLIENT");

    private Set<String> roles = Set.of();


    public boolean isUserInRole(String role) {
        return this.roles.stream().anyMatch(it -> it.equals(role) );
    }

    public boolean isUserLogged() {
        return  LOGGED_ROLES.stream().anyMatch(it -> roles.contains(it));
    }
}
