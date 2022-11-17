package pl.pas.parcellocker.repositories.hibernate;

import static pl.pas.parcellocker.configuration.PersistenceUtil.UNIT_NAME;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static pl.pas.parcellocker.configuration.PersistenceUtil.UNIT_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityManagerUtil {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME, configOverrides());

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private static Map<String, Object> configOverrides() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String envName : env.keySet()) {
            if (envName.contains("DB_HOST_PORT")) {
                configOverrides.put(
                    "jakarta.persistence.jdbc.url", "jdbc:postgresql://" + env.get(envName) + "/database"
                );
            }
        }
        return configOverrides;
    }
}
