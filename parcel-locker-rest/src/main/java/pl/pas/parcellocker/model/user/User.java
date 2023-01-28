package pl.pas.parcellocker.model.user;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.model.EntityModel;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_ROLE")
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public abstract class User extends EntityModel {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^\\d{9}$")
    private String telNumber;

    @JsonbTransient
    private String password;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    protected User(String firstName, String lastName, String telNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.password = password;

        active = true;
    }

    protected User(User user) {
        this.id = user.getId();
        this.telNumber = user.getTelNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.active = user.isActive();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
