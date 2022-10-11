package pl.pas.parcellocker.model;

import pl.pas.parcellocker.exceptions.ClientException;

public class Client {
    private final String firstName;
    private final String lastName;
    private final String telNumber;
    private final boolean archive;

    public Client(String firstName, String lastName, String telNumber) {
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;

        archive = false;
    }

    private void validateName(String name) {
        if (name.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

}
