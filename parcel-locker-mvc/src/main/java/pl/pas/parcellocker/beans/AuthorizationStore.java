package pl.pas.parcellocker.beans;

import jakarta.inject.Named;
import jakarta.ws.rs.core.NewCookie;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
@Named
@Getter
@Setter
public class AuthorizationStore implements Serializable {
    NewCookie jwtToken;
}
