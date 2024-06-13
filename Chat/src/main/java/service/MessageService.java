package service;

import entity.Group;
import entity.Message;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import static java.util.Collections.emptyList;

@Path("messages")
public class MessageService {
    public static ConcurrentMap<Long, Message> messageDb = new ConcurrentHashMap<>();

    @POST
    //TODO POST /messages HTTP/1.1
    //@Path("create")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Message Creat(Message message){

        //TODO Math.toIntExact wandelt long zu int?
        messageDb.put(message.getMessageid(),message);
        if (message != null){
            System.out.println("message eingefügt");
            return message;

        }else { throw new IllegalStateException("Message Fehler");}
    }

    @GET
    @Path("all")
    public Collection<Message> getAllMessage()  {
        return messageDb.values();
    }


    // GET /messages/group?groupId={groupId}
    @GET
    @Path("/group")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Message> getMessagesByGroupId(@QueryParam("groupId") String groupId) {
        if (groupId != null && !groupId.isEmpty()) {
            Group group = GroupService.groupDb.get(groupId);
            if (group != null) {
                return group.getMessages();
            }
        }
        return emptyList(); // Leere Liste zurückgeben, wenn kein `groupId` gefunden wird
    }

    // GET /messages/user?forUserId={userId}
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Message> getMessagesByUserId(@QueryParam("forUserId") String userId) {

        if (userId == null || userId.isEmpty()) {
            return emptyList();
        }

        Collection<Message> result = new ArrayList<>();
        for (Message message : messageDb.values()) {
            if (message.getUser() != null && userId.equals(message.getUser().getUserid())) {
                result.add(message);
            }
        }

        System.out.println(result);
        return result;

    }

    @DELETE
    @Path("{messageid}")
    public Message delete(@PathParam("messageid") Long messageId) {

        Message message = messageDb.remove(messageId);

        if(message == null)
            throw new IllegalStateException("User gelöscht");
        return message;
    }

}
