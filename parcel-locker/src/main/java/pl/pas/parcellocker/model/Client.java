package pl.pas.parcellocker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.exceptions.ClientException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@TableGenerator(name="clientTable", initialValue=100000, allocationSize=1)
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="clientTable")
    private Long id;
    private String firstName;
    private String lastName;
    private String telNumber;
    private boolean isArchive;

    public Client(String firstName, String lastName, String telNumber) {
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;

        isArchive = false;
    }

    private void validateName(String name) {
        if (name.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public boolean isArchived() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        this.isArchive = archive;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " phone: " + telNumber + (isArchive ? " Archived" : " Actual");
    }

    public Long getId() {
        return id;
    }
}
