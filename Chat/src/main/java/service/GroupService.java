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

        // groupe nur
     Collection<Group> Group1 = new ArrayList<>();

     Group g1 =new Group("1","Group 1");
     Group g2 =new Group("2","Group 2");

     groupDb.put(1,g1);
     groupDb.put(2,g2);

     Group1.add(g1);
     Group1.add(g2);

     // groupe mit list von users
     Collection<User> UserL1 = new ArrayList<>();

     User ul3 =new User("3","Tom");
     User ul4 =new User("4","Hans");

     UserService.userDb.put(3,ul3);
     UserService.userDb.put(4,ul4);

     UserL1.add(ul3);
     UserL1.add(ul4);

     Group gU1 =new Group("3","Group 3",UserL1);
     Group gU2 =new Group("4","Group 4",UserL1);

     groupDb.put(3,gU1);
     groupDb.put(4,gU2);

     return groupDb.values();

    }

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

    //TODO noch nicht korrekt??
    @Path("{groupid}/user/{userid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getGroupsByUserID(@PathParam("groupid") int groupid, @PathParam("userid") int userid) {
        Group group = groupDb.get(groupid);
        if (group == null) {
            throw new IllegalStateException("No group found with this ID");
        }

        for (User user : group.getUsers()) {
            if (Integer.parseInt(user.getUserid()) == userid) {
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
