package pl.pas.parcellocker.model.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@DiscriminatorValue("CLIENT")
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    @Builder
    public Client(final String firstName, final String lastName, final String telNumber) {
        super(firstName, lastName, telNumber);
    }

    public Client(User user) {
        super(user);
    }
}
