package pl.pas.parcellocker.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

import java.util.Optional;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class UserIdentityStore implements IdentityStore {

    @Inject
    UserManager userManager;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
            Optional<User> optionalUser = Optional.ofNullable(userManager.getUser(credential.getCaller()));

            if (!optionalUser.isPresent()) {
                return INVALID_RESULT;
            }

            User user = optionalUser.get();
            if (credential.getPassword().compareTo("dupa") && user.isActive()) { //TODO password kurwa
                return new CredentialValidationResult(user.getTelNumber(), Set.of("CLIENT")); //TODO role
            }

            return INVALID_RESULT;
    }
}
