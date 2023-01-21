package pl.pas.parcellocker.model.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MODERATOR")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Moderator extends User {

    @Builder
    public Moderator(final String firstName, final String lastName, final String telNumber, final String password) {
        super(firstName, lastName, telNumber, password);
        setRole(UserRole.MODERATOR);
    }

    public Moderator(User user) {
        super(user);
    }
}
