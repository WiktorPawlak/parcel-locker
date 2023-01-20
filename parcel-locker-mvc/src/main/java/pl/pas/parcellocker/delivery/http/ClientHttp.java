package pl.pas.parcellocker.delivery.http;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@ApplicationScoped
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ClientHttp {

    ClientBuilder clientBuilder = ClientBuilder.newBuilder();
    Client client = clientBuilder.build();
    WebTarget target;

    public void setPathForRemoteCall(String path) {
        target = client.target(path);
    }
}