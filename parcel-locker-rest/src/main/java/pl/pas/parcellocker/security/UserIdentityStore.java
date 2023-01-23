package pl.pas.parcellocker.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

import java.util.Optional;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class UserIdentityStore implements IdentityStore {

    @Inject
    private UserRepository clientRepository;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
            Optional<User> optionalUser = clientRepository.findByTelNumber(credential.getCaller());
            if (!optionalUser.isPresent()) {
                return INVALID_RESULT;
            }

            User user = optionalUser.get();
            if (credential.getPassword().compareTo(user.getPassword()) && user.isActive()) {
                return new CredentialValidationResult(user.getTelNumber(), Set.of(user.getRole().name()));
            }

            return INVALID_RESULT;
    }
}
