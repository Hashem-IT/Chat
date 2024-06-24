/*
package service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Properties;

@Path("producer")
public class ProducerService {
    private static final String TOPIC = "my-topic-1";
    private static final String BOOTSTRAP_SERVERS = "kafka-server:9092";

    private Producer<String, String> createProducer() {
        //TODO 1: Properties
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        //TODO 2: serializer
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    @POST
    @Path("send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(String message) {
        //TODO 3: producer wie stub
        // kay und value : <String, String>
        System.out.println("Nachricht1 gesendet: " + message);
        try (Producer<String, String> producer = createProducer()) {
            //TODO 4: ProducerRecord
            //TODO 5: send
            producer.send(new ProducerRecord<>(TOPIC, message));
            System.out.println("Nachricht gesendet: " + message);
            return Response.ok("Nachricht gesendet").build();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Nachricht2 gesendet: " + message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fehler beim Senden der Nachricht").build();
        }
    }
}

*/

package service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import jakarta.ws.rs.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("producer")
public class ProducerService {

    private static final String TOPIC = "my-topic-1"; // Topic-Name
    private static final String BOOTSTRAP_SERVERS = "kafka-server:9092"; // Adresse des Kafka-Servers
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    @POST
    @Path("sendsingle")
    public void sendMessage (String message){

        try (Producer<String, String> producer = createProducer()) {
                String key = Integer.toString(new Random().nextInt());

                producer.send(new ProducerRecord<>(TOPIC, key, message));
                System.out.println("sent single Messgage " + key + ": " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
