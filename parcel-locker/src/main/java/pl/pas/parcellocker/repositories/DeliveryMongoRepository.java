package pl.pas.parcellocker.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.bson.conversions.Bson;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.repositories.kafka.Topics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class DeliveryMongoRepository extends AbstractMongoRepository<Delivery> {

    private final ProducerHandler producerHandler;
    private final ObjectMapper objectMapper;

    public DeliveryMongoRepository(ProducerHandler producerHandler) {
        super("deliveries", Delivery.class);
        Topics.createTopic();
        this.producerHandler = producerHandler;
        this.objectMapper = new ObjectMapper();
    }

    public void update(Delivery delivery) {
        MongoCollection<Delivery> deliveryCollection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.eq("_id", delivery.getId());

        Bson setUpdate = Updates.combine(
            Updates.set("shipper", delivery.getShipper()),
            Updates.set("lastname", delivery.getReceiver()),
            Updates.set("status", delivery.getStatus()),
            Updates.set("pack", delivery.getPack()),
            Updates.set("locker", delivery.getLocker()),
            Updates.set("archived", delivery.isArchived())
        );

        deliveryCollection.updateOne(filter, setUpdate);
    }

    public List<Delivery> findByClient(Client client) {
        MongoCollection<Delivery> collection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.eq("receiver._id", client.getId());
        return collection.find().filter(filter).into(new ArrayList<>());
    }

    public List<Delivery> findArchivedByClient(Client client) {
        MongoCollection<Delivery> collection = parcelLocker.getCollection(collectionName, Delivery.class);
        Bson filter = Filters.and(
            Filters.eq("receiver._id", client.getId()),
            Filters.eq("status", DeliveryStatus.RECEIVED)
        );
        return collection.find().filter(filter).into(new ArrayList<>());
    }

    @Override
    public void add(Delivery object) {
        super.add(object);
        sendEvent(object);
    }

    private void sendEvent(Delivery delivery) {
        try {
            ProducerRecord<UUID, String> record = new ProducerRecord<>(
                Topics.DELIVERY_TOPIC,
                delivery.getId(),
                objectMapper.writeValueAsString(delivery));

            Future<RecordMetadata> sent = producerHandler.sentEvent(record);
            sent.get();
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
