package service;

import entity.Group;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("groups")
public class GroupService {
    public static ConcurrentMap<Integer, Group> groupDb = new ConcurrentHashMap<>();

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group Creat(Group group){

        //TODO Math.toIntExact wandelt long zu int?
        groupDb.put(Integer.parseInt(group.getGroupid()),group);
        if (group != null){
            System.out.println("groups eingefügt");
            return group;

        }else { throw new IllegalStateException("groups Fehler");}
    }

    @GET
    @Path("{groupid}")
    public Group getGroupById(@PathParam("groupid") int GroupId) {

        Group group = groupDb.get(GroupId);

        if(GroupId == Integer.parseInt(group.getGroupid()))
            return group;
        else
            throw new IllegalStateException("Es gibt kein groups");
    }

 @GET
    @Path("all")
    public Collection<Group> getAllGroup() {
        return groupDb.values();

    }

    //TODO get alle Users, die in groupid sind
    @GET
    @Path("{groupid}/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<User> getGroupsByUser(@PathParam("groupid") int groupid) {
        Group group = groupDb.get(groupid);

        if (group == null) {
            throw new IllegalStateException("Es gibt keine group mit dieser ID");
        }

        return group.getUsers();
    }

    //TODO gib mir die User Info , die in groupe steht
    @GET
    @Path("{groupid}/user/{userid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getUserfromGroupid(@PathParam("groupid") int groupid, @PathParam("userid") int userid) {
        Group group = groupDb.get(groupid);

        if (group == null) { throw new IllegalStateException("No group found with this ID");  }

        for (User user : group.getUsers()) {
            if (Integer.parseInt(user.getUserid()) == userid) {
                System.out.println(user.toString());
                return user;
            }
        }
        throw new IllegalStateException("No user found with this ID in the specified group");

    }

    @DELETE
    @Path("{groupid}")
    public Group delete(@PathParam("groupid") int GroupId) {

        Group group = groupDb.remove(GroupId);

        if(groupDb.get(GroupId) == null)
            throw new IllegalStateException("User gelöscht");
        return group;

    }
}
