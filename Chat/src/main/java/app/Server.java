package app;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.server.ResourceConfig;
import service.GroupService;
import service.MessageService;
import service.UserService;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {
        // Create configuration object for webserver instance
        ResourceConfig config = new ResourceConfig();
        // Register REST-resources (i.e. service classes) with the webserver
        config.register(ServerExceptionMapper.class);

        //TODO*******************
        config.register(MessageService.class);
        config.register(GroupService.class);
        config.register(UserService.class);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(config, HttpHandler.class);

        server.createContext("/restapi", handler);
        server.start();

        // Show dialogue in order to prevent premature ending of server(s)
       /* JOptionPane.showMessageDialog(null, "Stop server...");
        server.stop(0);*/
    }
}
