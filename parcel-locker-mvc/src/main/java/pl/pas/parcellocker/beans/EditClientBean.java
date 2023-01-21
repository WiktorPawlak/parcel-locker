package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.user.User;

import java.io.Serializable;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class EditClientBean implements Serializable {

    UserDto currentUser;
    String userType;
    Client client = ClientBuilder.newClient();

    public String edit() {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients")
                .path("{id}");

        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, "Client");
        final UserDto clientDto = UserDto.builder()
                .firstName(userBasedOnType.getFirstName())
                .lastName(userBasedOnType.getLastName())
                .telNumber(userBasedOnType.getTelNumber())
                .build();
        target.resolveTemplate("id", currentUser.getId()).request().put(Entity.json(clientDto));

        return "allUsers";
    }
}