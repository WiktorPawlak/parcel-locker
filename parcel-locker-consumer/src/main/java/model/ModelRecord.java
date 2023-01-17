package model;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
public class ModelRecord {
    @BsonId
    UUID id;

    @BsonProperty("value")
    String value;

    @BsonProperty("topic")
    String topic;

    @BsonProperty("partition")
    int partition;


    @BsonCreator
    public ModelRecord(@BsonId UUID id,
                       @BsonProperty("topic")String topic,
                       @BsonProperty("partition")int partition,
                       @BsonProperty("value")String value) {
        this.id = id;
        this.topic = topic;
        this.partition = partition;
        this.value = value;
    }

    static public ModelRecord of(ConsumerRecord<UUID, String> record) {
        return new ModelRecord(
                record.key(),
                record.topic(),
                record.partition(),
                record.value()
        );
    }
}
