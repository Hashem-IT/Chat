package service;

import entity.Group;
import entity.Message;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
/*
    //TODO get alle Users, die in groupid sind
    @GET
    @Path("{groupid}/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<User> getGroupsByUser(@PathParam("groupid") int groupid) {
        Group group = groupDb.get(groupid);

        if (group == null) {
            throw new IllegalStateException("Es gibt keine group mit dieser ID");
        }

        return group.getMembers();
    }*/

   /* //TODO gib mir die User Info , die in groupe steht
    @GET
    @Path("{groupid}/user/{userid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getUserfromGroupid(@PathParam("groupid") int groupid, @PathParam("userid") int userid) {
        Group group = groupDb.get(groupid);

        if (group == null) { throw new IllegalStateException("No group found with this ID");  }

        for (User user : group.getMembers()) {
            if (Integer.parseInt(user.getUserid()) == userid) {
                System.out.println(user.toString());
                return user;
            }
        }
        throw new IllegalStateException("No user found with this ID in the specified group");

    }*/

   /* //http://localhost:8081/restapi/groups/addUserToGroup/3/3
    // gib ein Group einen user mehr
    @PUT
    @Path("addUserToGroup/{groupid}/{userid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group addUserToGroup(@PathParam("groupid") int groupid, @PathParam("userid") int userid) {
        Group group = groupDb.get(groupid);
        User user = UserService.userDb.get(userid);

        if (group == null || user == null) {
            throw new IllegalStateException("Vorlesung oder Dozent nicht gefunden");
        }

        // Überprüfen, ob die Gruppe bereits Benutzer hat
        Collection<User> members = group.getMembers();
        if (members == null) {
            members = new ArrayList<>();
            group.setMembers(members);
        }

        // Benutzer zur bestehenden Sammlung hinzufügen
        members.add(user);

        // Gruppe in die Datenbank zurücksetzen
        groupDb.put(groupid, group);

        return group;
    }*/

    //http://localhost:8081/restapi/groups/addMessageToGroup/3/4
    // gib ein Group einen Message mehr
    //TODO :Caused by: com.sun.istack.SAXException2: Ein Zyklus wird in dem Objektdiagramm ermittelt. Dies verursacht ein endlos tiefes
    // XML: Group{groupid='10', name='Test 10'} -> Message{messageid=10, text='Test 10', timestamp=Mon Jan 10 01:00:00 CET 2000} -> Group{groupid='10', name='Test 10'}
    @PUT
    @Path("addMessageToGroup/{groupid}/{messageid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group addMessageToGroup(@PathParam("groupid") int groupid, @PathParam("messageid") int messageid) {
        Group group = groupDb.get(groupid);
        Message message = MessageService.messageDb.get(messageid);

        System.out.println(group.toString());
        System.out.println(message.toString());

        if (group == null || message == null) {
            throw new IllegalStateException("Vorlesung oder Dozent nicht gefunden");
        }


        // Überprüfen, ob die Gruppe bereits Nachrichten hat
        Collection<Message> messages = group.getMessages();
        if (messages == null) {
            messages = new ArrayList<>();
            group.setMessages(messages);
        }

        // Nachricht zur bestehenden Sammlung hinzufügen
        messages.add(message);

        // Gruppe in die Datenbank zurücksetzen
        groupDb.put(groupid, group);

        return group;
    }

    // http://localhost:9000/restapi/groups/group/10/messagesZeit?zeit=2001-01-01T00:00:00
    // bestimmte Zeit in groupe
    @GET
    @Path("group/{groupid}/messagesZeit")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Message> getMessagesForGroupZeit(@PathParam("groupid") int groupid, @QueryParam("zeit") String zeit) {
        Group group = GroupService.groupDb.get(groupid);
        if (group == null) {
            throw new IllegalStateException("Gruppe nicht gefunden");
        }

        Collection<Message> result = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date afterDate = dateFormat.parse(zeit);

            for (Message message : group.getMessages()) {
                if (message.getTimestamp().after(afterDate)) {
                    result.add(message);
                }
            }
        } catch (Exception e) {
            throw new WebApplicationException("Ungültiges Datumsformat", 400);
        }

        return result;
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
