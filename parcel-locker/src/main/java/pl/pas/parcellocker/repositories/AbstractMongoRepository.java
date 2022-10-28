package pl.pas.parcellocker.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;

import java.util.ArrayList;
import java.util.List;

public class AbstractMongoRepository implements AutoCloseable {

    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private MongoCredential credential = MongoCredential
        .createCredential("admin", "admin", "admin".toCharArray());

    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
        .automatic(true)
        .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
        .build());

    private MongoClient mongoClient;
    private MongoDatabase parcelLocker;

    public void  initDbConnection() {
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
        parcelLocker = mongoClient.getDatabase("parcelLocker");
    }

    public void add(Client client) {
        MongoCollection<Client> clientsCollection = parcelLocker.getCollection("clients", Client.class);
        clientsCollection.insertOne(client);
    }

    public List<Client> findAll() {
        MongoCollection<Client> clientsCollection = parcelLocker.getCollection("clients", Client.class);
        return clientsCollection.find().into(new ArrayList<>());
    }

    public void update() {
        MongoCollection<Client> clientsCollection = parcelLocker.getCollection("clients", Client.class);
        Bson filter = Filters.eq("firstname", "Adam");
        Bson setUpdate = Updates.set("firstname", "Mati");
        clientsCollection.updateMany(filter, setUpdate);
    }

    @Override
    public void close() throws Exception {

    }
}
