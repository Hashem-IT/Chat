
package service.OptionA;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("users")
public class UserService {

    // Verwenden Sie String für die userId
    //db.put braucht id und hier ist String ,daten hier ist User
   // public static ConcurrentMap<String, User> userDb = new ConcurrentHashMap<>();

    //TODO hazelcast

    static IMap<String, User> userDb;

    static {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.getNetworkConfig().addAddress("hazel-one");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        userDb = client.getMap("users");
    }

    @POST
    // bracuht kein path mit user , weil ist schon von users gekommen.
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User Creat(User user){
        if (user == null) {
            throw new IllegalArgumentException("User darf nicht null sein");
        }
        userDb.put(user.getUserid(), user);
        System.out.println("User eingefügt: " + user);
        return user;
    }

    @GET
    @Path("{userid}")
    public User getUserById(@PathParam("userid") String userId) {
        User user = userDb.get(userId);
        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException("User nicht gefunden");
        }
    }

    @GET
    // bracuht kein path mit user , weil ist schon von users gekommen.
    public Collection<User> getAllUser() {
        return userDb.values();
    }

    @DELETE
    @Path("{userid}")
    public User delete(@PathParam("userid") String userId) {
        User user = userDb.remove(userId);
        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException("User nicht gefunden oder bereits gelöscht");
        }
    }
}

