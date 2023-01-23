package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.ClientHttp;
import pl.pas.parcellocker.model.user.User;

import static pl.pas.parcellocker.delivery.http.ModulePaths.CLIENTS_PATH;

@Named
@RequestScoped
@Getter
@Setter
@NoArgsConstructor
public class AddClientBean {

    @Inject
    ClientHttp moduleExecutor;

    UserDto currentUser = new UserDto();

    String userType = "Client";

    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
    }

    public String add() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, userType);
        final UserDto clientDto = UserDto.builder()
                .firstName(userBasedOnType.getFirstName())
                .password(userBasedOnType.getPassword())
                .lastName(userBasedOnType.getLastName())
                .telNumber(userBasedOnType.getTelNumber())
                .build();

        httpClient.post("/clients", clientDto);

        return "newUser";
    }
}