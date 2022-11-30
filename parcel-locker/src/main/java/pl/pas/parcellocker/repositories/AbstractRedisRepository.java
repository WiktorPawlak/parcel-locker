package pl.pas.parcellocker.repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pl.pas.parcellocker.model.MongoEntityModel;
import pl.pas.parcellocker.utils.PropertiesLoader;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

import java.util.Set;
import java.util.UUID;

public abstract class AbstractRedisRepository<T extends MongoEntityModel> implements AutoCloseable {

  protected static JedisPooled pool;
  private final Class<T> entityClass;
  protected final Jsonb jsonb = JsonbBuilder.create();
  protected final String prefix;

  public AbstractRedisRepository(Class<T> entityClass) {
    this.entityClass = entityClass;
    this.prefix = entityClass.getSimpleName() + ":";

    initDbConnection();
  }

  public void initDbConnection() {
    JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
    pool =
        new JedisPooled(
            new HostAndPort(
                PropertiesLoader.getProperty("redis.host"),
                Integer.parseInt(PropertiesLoader.getProperty("redis.port"))),
            clientConfig);

      Schema schema = new Schema().addTextField("$.telNumber", 1.0);
      IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON).setPrefixes(prefix);
    try{
        pool.ftDropIndex("parcel-locker");
    } catch (Exception e) {

    }

      try{
          pool.ftCreate("parcel-locker", IndexOptions.defaultOptions().setDefinition(rule), schema);
      } catch (Exception e) {

      }

  }

  public void add(T object) {
    String json = jsonb.toJson(object);
    pool.jsonSet(prefix + object.getId(), json);
    pool.expire(prefix + object.getId(), 3600);
  }

  public T findById(UUID id) {
    return pool.jsonGet(prefix + id, entityClass);
  }

  public Set<String> findAllKeys() {
    return pool.keys(prefix + "*");
  }

    public void remove(UUID id) {
        Set<String> keys = pool.keys(prefix + id);
        pool.del(keys.stream().findFirst().get());
    }

  public void clear() {
    Set<String> keys = pool.keys(prefix + "*");
    for (String key : keys) {
      pool.del(key);
    }
  }

    public boolean isConnected() {
        try {
            pool.getPool().getResource();
            return true;
        } catch (JedisConnectionException e) {
            return false;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
