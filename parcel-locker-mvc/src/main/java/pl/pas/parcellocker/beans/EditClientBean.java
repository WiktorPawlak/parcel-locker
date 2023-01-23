package pl.pas.parcellocker.beans;

import java.io.Serializable;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.user.User;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class EditClientBean implements Serializable {

    UserDto currentUser;
    String userType;
    HttpClient httpClient = new HttpClient();

    public String edit() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, "Client");
        final UserDto clientDto = UserDto.builder()
                .password(userBasedOnType.getPassword())
                .firstName(userBasedOnType.getFirstName())
                .lastName(userBasedOnType.getLastName())
                .build();

        httpClient.putJsonBody("/clients/" + currentUser.getId(), clientDto);

        return "allUsers";
    }
}