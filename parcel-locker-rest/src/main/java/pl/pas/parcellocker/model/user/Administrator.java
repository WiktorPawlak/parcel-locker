package pl.pas.parcellocker.model.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User {

    @Builder
    public Administrator(final String firstName, final String lastName, final String telNumber, final String password) {
        super(firstName, lastName, telNumber, password);
        setRole(UserRole.ADMINISTRATOR);
    }

    public Administrator(User user) {
        super(user);
    }
}
