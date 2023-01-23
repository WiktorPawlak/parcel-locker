package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
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
    HttpClient httpClient = new HttpClient();

    public String edit() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, "Client");
        final UserDto clientDto = UserDto.builder()
                .telNumber(userBasedOnType.getTelNumber())
                .password(userBasedOnType.getPassword())
                .firstName(userBasedOnType.getFirstName())
                .lastName(userBasedOnType.getLastName())
                .build();

        httpClient.put("/clients/" + currentUser.getId(), clientDto);

        return "allUsers";
    }
}