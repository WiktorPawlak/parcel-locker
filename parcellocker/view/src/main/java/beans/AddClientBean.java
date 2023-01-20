package beans;

import static delivery.http.ModulePaths.CLIENTS_PATH;

import delivery.http.ClientHttp;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.api.dto.ClientDto;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

@Named
@RequestScoped
@Getter
@Setter
@NoArgsConstructor
public class AddClientBean {

    @Inject
    ClientHttp moduleExecutor;

    User currentUser = new Client();

    String userType = "Client";

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
    }

    public String add() {
        currentUser = Utils.prepareUserBasedOnType(currentUser, userType);

        final ClientDto clientDto = ClientDto.builder()
            .firstName(currentUser.getFirstName())
            .lastName(currentUser.getLastName())
            .telNumber(currentUser.getTelNumber())
            .build();
        moduleExecutor.getTarget().request().post(Entity.json(clientDto));

        return "newUser";
    }
}
