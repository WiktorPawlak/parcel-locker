package repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractMongoRepository<T> implements AutoCloseable {

    protected final String collectionName;
    private final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private final MongoCredential credential = MongoCredential
        .createCredential("admin", "admin", "admin".toCharArray());

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
        .automatic(true)
        //.register(Package.class, pl.pas.parcellocker.model.List.class, Parcel.class)
        .conventions(Conventions.DEFAULT_CONVENTIONS)
        .build());
    private final Class<T> entityClass;
    protected MongoDatabase parcelLocker;
    private MongoClient mongoClient;

    public AbstractMongoRepository(String collectionName, Class<T> entityClass) {
        this.collectionName = collectionName;
        this.entityClass = entityClass;

        initDbConnection();
    }

    public void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
            .credential(credential)
            .applyConnectionString(connectionString)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .codecRegistry(CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry
            ))
            .build();

        mongoClient = MongoClients.create(settings);
        parcelLocker = mongoClient.getDatabase("consumer");
    }

    public void add(T object) {
        MongoCollection<T> collection = parcelLocker.getCollection(collectionName, entityClass);
        collection.insertOne(object);
    }

    public T findById(UUID id) {
        MongoCollection<T> collection = parcelLocker.getCollection(collectionName, entityClass);
        Bson filter = Filters.eq("_id", id);
        return collection.find().filter(filter).first();
    }

    public List<T> findAll() {
        MongoCollection<T> collection = parcelLocker.getCollection(collectionName, entityClass);
        return collection.find().into(new ArrayList<>());
    }


    @Override
    public void close() {
        mongoClient.close();
    }
}
