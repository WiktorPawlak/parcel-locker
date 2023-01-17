package pl.pas.parcellocker.repositories;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public interface ProducerHandler {
    Future<RecordMetadata> sentEvent(ProducerRecord record);
}
