package pl.pas.parcellocker.managers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.controllers.dto.ClientDto;
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
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
@NoArgsConstructor
public class UserManager {

    @Inject
    private PermissionValidator permissionValidator;

    @Inject
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

    public List<User> findAll() {
        return clientRepository.findAll();
    }

    public User findById(UUID id) {
        return clientRepository.findUserById(id)
            .orElseThrow();
    }


    public List<User> getUsersByPartialTelNumber(String telNumberPart) {
        List<User> foundUsers;
        if (telNumberPart == null) {
            foundUsers = clientRepository.findAll();
        } else {
            foundUsers = clientRepository.findByTelNumberPart(telNumberPart);
        }
        return foundUsers;
    }

    public synchronized User registerClient(String firstName, String lastName, String telNumber) {

        validateIfEmpty(firstName, lastName, telNumber);

        for (User user : clientRepository.findAll()) {
            if (user.getTelNumber().equals(telNumber))
                throw new ClientManagerException("Client with given telephone number already exits");
        }

        User newUser = new Client(firstName, lastName, telNumber);
        clientRepository.add(newUser);
        return newUser;
    }

    public User registerClient(User client) {
        for (User user : clientRepository.findAll()) {
            if (user.getTelNumber().equals(client.getTelNumber()))
                throw new ClientManagerException("Client with given telephone number already exits");
        }

        client.setActive(true);
        clientRepository.add(client);
        return client;
    }

    public void edit(UUID id, ClientDto clientDto) {
        Optional<User> userToEditOptional = clientRepository.findUserById(id);
        if (userToEditOptional.isPresent()) {
            User userToEdit = userToEditOptional.get();
            userToEdit.setFirstName(clientDto.getFirstName());
            userToEdit.setLastName(clientDto.getLastName());
            userToEdit.setTelNumber(clientDto.getTelNumber());
            clientRepository.update(userToEdit);
        }
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

    public User unregisterClient(User user) {
        if (user == null)
            throw new ClientManagerException("Client is a null!");

        getUser(user.getTelNumber());
        clientRepository.archive(user.getId());
        return user;
    }

    public User activateClient(User user) {
        if (user == null)
            throw new ClientManagerException("Client is a null!");

        getUser(user.getTelNumber());
        clientRepository.unarchive(user.getId());
        return user;
    }

    private void validateIfEmpty(String... values) {
        if (Arrays.stream(values).anyMatch(String::isEmpty))
            throw new ClientManagerException("Value is empty!");
    }
}
