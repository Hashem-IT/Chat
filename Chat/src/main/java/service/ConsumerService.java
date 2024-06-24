
package service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

 public class ConsumerService  implements  Runnable {
    private static final String TOPIC = "my-topic-1";
    private static final String BOOTSTRAP_SERVERS = "kafka-server:9092";
    private static final String GROUP_ID = "my-group-id";

    private KafkaConsumer<String, String> createConsumer() {
        //TODO 1: Properties
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        //TODO 2: deserializer
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        return new KafkaConsumer<>(props);
    }

    public void run() {
        //TODO 3: consumer
        try (KafkaConsumer<String, String> consumer = createConsumer()) {
            consumer.subscribe(Collections.singletonList(TOPIC));
            //TODO 3: ConsumerRecords
            //TODO 4: poll in jede 1000 MS

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(4000));
                System.out.printf("Test ......\n ");
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("received message: %s\n", record.value());
                }
            }

        }
    }
}







/*
package service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Path("consumer")
public class ConsumerService {

    private static final String TOPIC = "my-topic-1"; // Topic-Name
    private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Adresse des Kafka-Servers

    private KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.setProperty("group.id", "my-group-id"); // Eine eindeutige Gruppen-ID für den Konsumenten
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("auto.offset.reset", "earliest");

        return new KafkaConsumer<>(props);
    }

    @GET
    @Path("receive")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveMessages() {
        StringBuilder sb = new StringBuilder();
        try (KafkaConsumer<String, String> consumer = createConsumer()) {
            consumer.subscribe(Arrays.asList(TOPIC));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                //sb.append("received message: ").append(record.value()).append("\n");
                return Response.ok("received message:"+record.value() ).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fehler beim Empfangen der Nachrichten: " + e.getMessage() ).build();
        }
        return Response.ok(sb.toString()).build();
    }
}
*/
