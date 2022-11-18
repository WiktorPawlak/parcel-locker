package pl.pas.parcellocker.controllers;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import pl.pas.parcellocker.controllers.dto.LockerDto;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.managers.LockerManager;
import pl.pas.parcellocker.model.locker.Locker;

@RequestMapping(value = "/lockers")
public class LockerController {

    @Autowired
    private LockerManager lockerManager;

    @GetMapping("/{identityNumber}")
    public ResponseEntity getLocker(@PathVariable("identityNumber") String identityNumber) {
        try {
            return ResponseEntity.ok().body(lockerManager.getLocker(identityNumber));
        } catch (LockerManagerException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity addLocker(@Valid LockerDto lockerDto) {
        try {
            Locker newLocker = lockerManager.createLocker(
                lockerDto.identityNumber,
                lockerDto.address,
                lockerDto.numberOfBoxes
            );
            return ResponseEntity.status(CREATED).body(newLocker);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{identityNumber}")
    public ResponseEntity removeLocker(@PathVariable("identityNumber") String identityNumber) {
        try {
            lockerManager.removeLocker(identityNumber);
            return ResponseEntity.status(NO_CONTENT).build();
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}
