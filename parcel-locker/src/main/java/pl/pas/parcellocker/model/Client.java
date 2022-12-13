package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.configuration.SchemaConst;
import pl.pas.parcellocker.exceptions.ClientException;

import java.util.UUID;

@Entity(defaultKeyspace = SchemaConst.PARCEL_LOCKER_NAMESPACE)
@CqlName("clients")
@Getter
@NoArgsConstructor
public class Client extends AbstractEntity {

    private String firstName;
    private String lastName;

    @PartitionKey
    @CqlName("telNumber")
    private String telNumber;

    @ClusteringColumn
    @CqlName("active")
    private boolean active;

    public Client(String firstName, String lastName, String telNumber) {
        super(UUID.randomUUID());
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;

        active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private void validateName(String name) {
        if (name.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " phone: " + telNumber + (active ? " Archived" : " Actual");
    }
}
