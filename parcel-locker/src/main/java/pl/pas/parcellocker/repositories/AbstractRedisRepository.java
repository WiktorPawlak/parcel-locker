package pl.pas.parcellocker.repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pl.pas.parcellocker.model.MongoEntityModel;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

import java.util.Set;
import java.util.UUID;

public abstract class AbstractRedisRepository<T extends MongoEntityModel> implements AutoCloseable {

    private static JedisPooled pool;
    private Jsonb jsonb = JsonbBuilder.create();
    private final Class<T> entityClass;
    private String prefix;

    public void initDbConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPooled(new HostAndPort("localhost", 6379), clientConfig);
    }

    public AbstractRedisRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.prefix = entityClass.getSimpleName() + ":";

        initDbConnection();
    }

    public void add(T object) {
        String json = jsonb.toJson(object);
        pool.set(prefix + object.getId(), json);
        pool.expire(prefix + object.getId(), 3600);
    }

    public T findById(UUID id) {
        String json = pool.get(prefix + id);
        return  jsonb.fromJson(json, entityClass);
    }

    public Set<String> findAllKeys() {
      return  pool.keys(prefix + "*");
    }

    public void clear() {
        Set<String> keys = pool.keys(prefix + "*");
        for (String key : keys) {
            pool.del(key);
        }
    }

    @Override
    public void close() throws Exception {

    }

}
