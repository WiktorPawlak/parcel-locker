package pl.pas.parcellocker.controllers;


import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private UserManager userManager;

    @GetMapping("/{telNumber}")
    public ResponseEntity getClient(@PathVariable("telNumber") String telNumber) {
        try {
            return ResponseEntity.ok().body(userManager.getUser(telNumber));
        } catch (NoResultException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity getClientsByPhoneNumberPattern(@RequestParam("telNumber") String telNumber) {
        try {
            return ResponseEntity.ok().body(userManager.getUsersByPartialTelNumber(telNumber));
        } catch (NoResultException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity registerClient(@RequestParam("operatorId") UUID operatorID, @Valid ClientDto clientDTO) {
        try {
            User newUser = userManager.registerClient(operatorID, clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber);
            return ResponseEntity.status(CREATED).body(newUser);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity unregisterClient(@RequestParam("operatorId") UUID operatorId, String telNumber) {
        try {
            User user = userManager.getUser(telNumber);
            User unregisteredUser = userManager.unregisterClient(operatorId, user);
            return ResponseEntity.ok().body(unregisteredUser);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }
}
