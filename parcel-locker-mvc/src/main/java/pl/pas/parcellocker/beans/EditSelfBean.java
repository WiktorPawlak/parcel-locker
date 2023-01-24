package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
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
public class EditSelfBean implements Serializable {

    UserDto currentUser;
    String userType;
    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void getUser() {
        Response response = httpClient.get("/clients/self");
        currentUser = response.readEntity(new GenericType<>() {
        });
    }

    public String edit() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, "Client");
        final UserDto clientDto = UserDto.builder()
                .password(userBasedOnType.getPassword())
                .firstName(userBasedOnType.getFirstName())
                .lastName(userBasedOnType.getLastName())
                .build();

        httpClient.put("/clients/self", Entity.json(clientDto), Map.of("id", String.valueOf(currentUser.getId())));

        return "index";
    }
}