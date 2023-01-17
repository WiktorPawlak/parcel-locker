import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Consumer {

    public static final String CONSUMER_GROUP = "ConsumersGroup";
    private static KafkaConsumer<UUID, String> kafkaConsumer;

    public void initConsumer() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        //consumerConfig.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "clientconsumer");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Topics.KAFKA_NODES_STING);

        kafkaConsumer = new KafkaConsumer(consumerConfig);
        kafkaConsumer.subscribe(List.of(Topics.CLIENT_TOPIC));
    }

}
