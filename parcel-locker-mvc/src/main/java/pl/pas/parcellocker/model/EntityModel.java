package pl.pas.parcellocker.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class EntityModel implements Serializable {

    protected UUID id;

    protected Long version;

}
