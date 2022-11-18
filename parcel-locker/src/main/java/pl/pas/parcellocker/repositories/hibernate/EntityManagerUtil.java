package pl.pas.parcellocker.repositories.hibernate;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityManagerUtil {

    @PersistenceUnit(name = "parcel-locker-unit")
    private static EntityManager em;

    public static EntityManager getEntityManager() {
        return em;
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
