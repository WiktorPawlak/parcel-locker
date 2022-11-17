package pl.pas.parcellocker.model.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void add(User user);

    void archive(UUID clientId);

    List<User> findAll();

    Optional<User> findByTelNumber(String telNumber);

    List<User> findByTelNumberPart(String telNumberPart);

    Optional<User> findUserById(UUID uuid);
}
