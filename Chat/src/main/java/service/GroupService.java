package service;

import entity.Group;
import entity.Message;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("groups")
public class GroupService {
    public static ConcurrentMap<String, Group> groupDb = new ConcurrentHashMap<>();

    //nicht nötig
    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group Creat(Group group){
        if (group == null) {
            throw new IllegalArgumentException("Group darf nicht null sein");
        }
        if (group.getGroupid() == null || group.getGroupid().isEmpty()) {
            throw new IllegalArgumentException("GroupId darf nicht null oder leer sein");
        }
        if (group.getMembers() == null) {
            group.setMembers(new ArrayList<>());  // Initialisieren der Mitglieder-Liste, wenn sie null ist
        }
        groupDb.put(group.getGroupid(), group);
        System.out.println("Group eingefügt: " + group);
        return group;
    }
    // POST /groups/{groupid}/messages
    // diese Code POST /groups/X93f83x9Z/messages HTTP/1.1
    @POST
    @Path("{groupid}/messages")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group createMessageForGroup(@PathParam("groupid") String groupId, Message message) {
        Group group = groupDb.get(groupId);
        if (group == null) {
            throw new WebApplicationException("Group nicht gefunden", 404);
        }

        if (message == null || message.getMessageid() == null) {
            throw new IllegalArgumentException("Message und MessageId dürfen nicht null sein");
        }

        Collection<Message> messages = group.getMessages();
        if (messages == null) {
            messages = new ArrayList<>();
            group.setMessages(messages);
        }

        messages.add(message);
        System.out.println("Nachricht zu Gruppe hinzugefügt: " + message);

        // Optionale: Rückgabe der Gruppe mit der neuen Nachricht
        return group;
    }
    //http://localhost:9000/restapi/groups/X93f83x9Z
    // gib ein Group einen Message mehr
    // PUT /groups/X93f83x9Z HTTP/1.1
    @PUT
    @Path("{groupid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Group addMessageToGroup(@PathParam("groupid") String groupid, Message message) {
        Group group = groupDb.get(groupid);
        if (group == null) {
            throw new WebApplicationException("Group nicht gefunden", 404);
        }

        // Überprüfen, ob die Gruppe bereits Nachrichten hat
        Collection<Message> messages = group.getMessages();
        if (messages == null) {
            messages = new ArrayList<>();
            group.setMessages(messages);
        }

        // Nachricht zur bestehenden Sammlung hinzufügen
        messages.add(message);

        return group;
    }
    @GET
    @Path("{groupid}")
    public Group getGroupById(@PathParam("groupid") String groupId) {

        Group group = groupDb.get(groupId);

        if(groupId.equals(group.getGroupid()))
            return group;
        else
            throw new IllegalStateException("Es gibt kein groups");
    }

 @GET
    @Path("all")
    public Collection<Group> getAllGroup() {
        return groupDb.values();

    }

    @GET
    @Path("{groupid}/messages")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMessagesByGroup(@PathParam("groupid") String groupid) {
        Group group = groupDb.get(groupid);
        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Group nicht gefunden").build();
        }

        Collection<Message> messages = group.getMessages();
        return Response.ok(messages).build();
    }

    @DELETE
    @Path("{groupid}")
    public Group delete(@PathParam("groupid") String groupId) {

        Group group = groupDb.remove(groupId);

        if(groupDb.get(groupId) == null)
            throw new IllegalStateException("User gelöscht");
        return group;

    }
}
