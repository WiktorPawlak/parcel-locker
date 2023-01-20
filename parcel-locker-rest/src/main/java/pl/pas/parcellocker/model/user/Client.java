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
    public Client(final String firstName, final String lastName, final String telNumber, final String password) {
        super(firstName, lastName, telNumber, password);
        setRole(UserRole.CLIENT);
    }

    public Client(User user) {
        super(user);
    }
}
