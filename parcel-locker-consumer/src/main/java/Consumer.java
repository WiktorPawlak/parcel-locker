import model.ModelRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import repositories.RecordRepository;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Consumer {

    public static final String CONSUMER_GROUP = "ConsumersGroup";
    private KafkaConsumer<UUID, String> kafkaConsumer;
    private List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();


    private RecordRepository recordRepository = new RecordRepository();

    public void initConsumer() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        //consumerConfig.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "clientconsumer");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Topics.KAFKA_NODES_STRING);

        for (int i = 0; i < 2; i++) {
            KafkaConsumer<UUID, String> consumer = new KafkaConsumer(consumerConfig);
            consumer.subscribe(List.of(Topics.CLIENT_TOPIC));
            consumerGroup.add(consumer);
        }

        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            consume(consumer);
        }

    }

    private void consume(KafkaConsumer<UUID, String> consumer) {
        try{
            consumer.poll(0);
            Set<TopicPartition> consumerAssignment = consumer.assignment();


            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);

            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);
                for (ConsumerRecord<UUID, String> record : records) {
                    System.out.println(formatMessage(record));
                    recordRepository.add(ModelRecord.of(record));
                }
            }
        } catch (WakeupException we) {
            we.printStackTrace();
        }
    }

    private String formatMessage(ConsumerRecord<UUID, String> record) {
        MessageFormat formatter =
                new MessageFormat("Temat {0}, partycja {1}, klucz {3}, wartość {4}, offset {5, number, integer}");
        return formatter.format(new Object[] {
                record.topic(),
                record.partition(),
                record.key(),
                record.value(),
                record.offset()
        });
    }

}
