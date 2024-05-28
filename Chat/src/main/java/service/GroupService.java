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
  /*  @GET
    @Path("all")
    public List<Group> getAllGroup() {

        List<User> Group1 = new ArrayList<>();

        User u1 =new User("1","Yassin");
        User u2 =new User("2","Lukas");

        UserService.userDb.put(1,u1);
        UserService.userDb.put(2,u2);

        Group1.add(u1);
        Group1.add(u2);

        for (ConcurrentMap.Entry<Integer, User> UserEntry : UserService.userDb.entrySet()) {
            student = studentEntry.getValue();
            studentlist.add(student);
        }

    }*/

    @DELETE
    @Path("{groupid}")
    public Group delete(@PathParam("groupid") int GroupId) {

        Group group = groupDb.remove(GroupId);

        if(groupDb.get(GroupId) == null)
            throw new IllegalStateException("User gelöscht");
        return group;

    }
}
