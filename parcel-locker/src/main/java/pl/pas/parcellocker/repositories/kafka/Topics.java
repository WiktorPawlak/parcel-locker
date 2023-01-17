package pl.pas.parcellocker.repositories.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Topics {
    public static final String DELIVERY_TOPIC = "deliveries";
    public static final String KAFKA_NODES_STRING = "kafka1:9192,kafka2:9292,kafka3:9392";
    private static int PARTITION_NUMBER = 3;
    private static short REPLICATION_FACTOR = 2;
    private static int TIMEOUT_MS = 10000;

    public static void createTopic() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_NODES_STRING);

        try (Admin admin = Admin.create(props)) {
            NewTopic newTopic = new NewTopic(Topics.DELIVERY_TOPIC, PARTITION_NUMBER, REPLICATION_FACTOR);
            CreateTopicsOptions options = new CreateTopicsOptions()
                .timeoutMs(TIMEOUT_MS)
                .validateOnly(false)
                .retryOnQuotaViolation(true);
            CreateTopicsResult result = admin.createTopics(List.of(newTopic), options);
            KafkaFuture<Void> futureResult = result.values().get(Topics.DELIVERY_TOPIC);
            futureResult.get();
        } catch (ExecutionException | InterruptedException exception) {
            log.error(exception.getMessage());
        }
    }
}
