package app;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.server.ResourceConfig;

//TODO Option B , 3 Services
import service.ConsumerService;
import service.OptionB.MessageService;
import service.OptionB.UserService;
import service.OptionB.GroupService;
import service.ProducerService;

/*//TODO Option A , 2 Services
import service.OptionA.GroupService;
import service.OptionA.UserService;*/

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {
        // Create configuration object for webserver instance
        ResourceConfig config = new ResourceConfig();
        // Register REST-resources (i.e. service classes) with the webserver
        config.register(ServerExceptionMapper.class);

        //TODO Option A , 2 Services
        config.register(GroupService.class);
        config.register(UserService.class);

        /*//TODO Option B , 3 Services
        config.register(GroupService.class);
        config.register(UserService.class);
        config.register(MessageService.class);*/

        // TODO Kafka service
        /*
        config.register(ProducerService.class);
        //config.register(ConsumerService.class);
        // thread kafka consumer starten

        Thread t = new Thread(new ConsumerService());
        t.start();
*/

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(config, HttpHandler.class);

        server.createContext("/restapi", handler);
        server.start();

        System.out.println("Server läuft auf http://localhost:8000/restapi");

        // Show dialogue in order to prevent premature ending of server(s)
       /* JOptionPane.showMessageDialog(null, "Stop server...");
        server.stop(0);*/
    }
}
