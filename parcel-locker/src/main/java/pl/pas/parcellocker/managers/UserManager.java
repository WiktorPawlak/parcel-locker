package pl.pas.parcellocker.managers;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.exceptions.PermissionValidationException;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;
import pl.pas.parcellocker.security.PermissionValidator;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Service
public class UserManager {

    @Autowired
    private PermissionValidator permissionValidator;

    @Autowired
    private UserRepository clientRepository;

    public UserManager(UserRepository clientRepository, PermissionValidator permissionValidator) {
        this.clientRepository = clientRepository;
        this.permissionValidator = permissionValidator;
    }

    public User getUser(String telNumber) {
        validateIfEmpty(telNumber);

        var client = clientRepository.findByTelNumber(telNumber);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientManagerException("Client with given telephone number not found");
        }
    }

    public List<User> getUsersByPartialTelNumber(String telNumberPart) {
        validateIfEmpty(telNumberPart);

        return clientRepository.findByTelNumberPart(telNumberPart);
    }

    public synchronized User registerClient(UUID operatorId, String firstName, String lastName, String telNumber) {
        if (!permissionValidator.checkPermissions(operatorId, List.of(Administrator.class))) {
            throw new PermissionValidationException("Not sufficient access rights");
        }

        validateIfEmpty(firstName, lastName, telNumber);

        for (User user : clientRepository.findAll()) {
            if (user.getTelNumber().equals(telNumber))
                throw new ClientManagerException("Client with given telephone number already exits");
        }

        User newUser = new Client(firstName, lastName, telNumber);
        clientRepository.add(newUser);
        return newUser;
    }

    public User unregisterClient(UUID operatorId, User user) {
        if (!permissionValidator.checkPermissions(operatorId, List.of(Administrator.class, Moderator.class))) {
            throw new PermissionValidationException("Not sufficient access rights");
        }
        if (user == null)
            throw new ClientManagerException("Client is a null!");

        getUser(user.getTelNumber());
        clientRepository.archive(user.getId());
        return user;
    }

    private void validateIfEmpty(String... values) {
        if (Arrays.stream(values).anyMatch(String::isEmpty))
            throw new ClientManagerException("Value is empty!");
    }
}
