package pl.pas.parcellocker.beans;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.CredentialsDto;


@ViewScoped
@Named
@Getter
@Setter
public class AuthBean implements Serializable {

    CredentialsDto credentials;

    Client client = ClientBuilder.newClient();

    public String logIn() {
        WebTarget webTarget = this.client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/auth/login");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.post(Entity.json(this.credentials));
        return "index";
    }

    public String logOut() {
        WebTarget webTarget = this.client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/auth/logout");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.post(null);
        return "index";
    }
}