package service;

import entity.Group;
import entity.Message;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("users")
public class UserService {

    public static ConcurrentMap<Integer, User> userDb = new ConcurrentHashMap<>();

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User Creat(User user){

        //TODO Math.toIntExact wandelt long zu int?
        userDb.put(Integer.parseInt(user.getUserid()),user);
        if (user != null){
            System.out.println("user eingefügt");
            return user;

        }else { throw new IllegalStateException("user Fehler");}
    }

    @GET
    @Path("{userid}")
    public User getUserById(@PathParam("userid") int UserId) {

        User user = userDb.get(UserId);

        if(UserId == Integer.parseInt(user.getUserid()))
            return user;
        else
            throw new IllegalStateException("Es gibt kein user");
    }

    @GET
    @Path("all")
    public Collection<User> getAllUser() {
        return userDb.values();
    }

    @DELETE
    @Path("{userid}")
    public User delete(@PathParam("userid") int UserId) {

        User user = userDb.remove(UserId);

        if(userDb.get(UserId) == null)
            throw new IllegalStateException("User gelöscht");
        return user;

    }
}
