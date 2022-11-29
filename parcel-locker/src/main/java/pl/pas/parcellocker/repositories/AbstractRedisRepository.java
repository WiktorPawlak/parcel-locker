package pl.pas.parcellocker.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.MongoEntityModel;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;

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
    }

    public T findById(UUID id) {
        String json = pool.get(prefix + id);
        return  jsonb.fromJson(json, entityClass);
    }

    @Override
    public void close() throws Exception {

    }

}
