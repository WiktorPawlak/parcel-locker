package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.user.User;

@Named
@RequestScoped
@Getter
@Setter
@NoArgsConstructor
public class AddClientBean {

    UserDto currentUser = new UserDto();

    String userType = "Client";

    HttpClient httpClient = new HttpClient();


    public String add() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, userType);
        final UserDto clientDto = UserDto.builder()
                .firstName(userBasedOnType.getFirstName())
                .password(userBasedOnType.getPassword())
                .lastName(userBasedOnType.getLastName())
                .telNumber(userBasedOnType.getTelNumber())
                .build();

        httpClient.post("/clients", clientDto);

        return "index";
    }
}