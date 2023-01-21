package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.ClientHttp;
import pl.pas.parcellocker.model.user.Client;
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

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
    }

    public String add() {
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients");

        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, userType);
        final UserDto clientDto = UserDto.builder()
                .firstName(userBasedOnType.getFirstName())
                .lastName(userBasedOnType.getLastName())
                .telNumber(userBasedOnType.getTelNumber())
                .build();
        target.request().post(Entity.json(clientDto));

        return "newUser";
    }
}