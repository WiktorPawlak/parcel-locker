package repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

public abstract class AbstractMongoRepository<T> implements AutoCloseable {

    private final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private final MongoCredential credential = MongoCredential
        .createCredential("admin", "admin", "admin".toCharArray());

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
        .automatic(true)
        .conventions(Conventions.DEFAULT_CONVENTIONS)
        .build());
    private final MongoCollection<T> collection;
    protected MongoDatabase parcelLocker;
    private MongoClient mongoClient;

    public AbstractMongoRepository(String collectionName, Class<T> entityClass) {
        initDbConnection();
        this.collection = parcelLocker.getCollection(collectionName, entityClass);
    }

    public void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
            .credential(credential)
            .applyConnectionString(connectionString)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry
            ))
            .build();

        mongoClient = MongoClients.create(settings);
        parcelLocker = mongoClient.getDatabase("consumer");
    }

    public void add(T object) {
        collection.insertOne(object);
    }


    @Override
    public void close() {
        mongoClient.close();
    }
}
