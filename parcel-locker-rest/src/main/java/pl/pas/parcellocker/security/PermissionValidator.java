package pl.pas.parcellocker.security;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.model.user.UserRepository;


@NoArgsConstructor
public class PermissionValidator {

    @Inject
    private UserRepository clientRepository;

    public PermissionValidator(final UserRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean checkPermissions(UUID id, List<?> roles) {
        var user = clientRepository.findUserById(id).orElseThrow();
        return roles.stream().anyMatch(role -> user.getClass() == role);
    }
}
