package pl.pas.parcellocker.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class VersionModel {
    @Version
    protected Long version;
}
