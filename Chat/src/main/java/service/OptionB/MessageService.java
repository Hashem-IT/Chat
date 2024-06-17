package service.OptionB;

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
    // bracuht kein path mit messages , weil ist schon von messages gekommen.
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Message Creat(Message message){

        if (message == null || message.getMessageid() == null) {
            throw new IllegalArgumentException("Message oder MessageId darf nicht null sein");
        }

        messageDb.put(message.getMessageid(), message);

        if (message.getSender() != null) {
            UserService.userDb.put(message.getSender().getUserid(), message.getSender());
        }

        if (message.getReceiver() != null) {
            UserService.userDb.put(message.getReceiver().getUserid(), message.getReceiver());
        }

        if (message.getSender() != null && message.getReceiver() != null) {
            Group group = GroupService.groupDb.get(message.getSender().getUserid());
            if (group != null) {
                Collection<Message> messages = group.getMessages();
                if (messages == null) {
                    messages = new ArrayList<>();
                    group.setMessages(messages);
                }
                messages.add(message);
            }
        }

        System.out.println("Message eingefügt: " + message);
        return message;
    }
    //TODO muss messages in groupe rein.
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Message> getMessages(@QueryParam("groupId") String groupId, @QueryParam("forUserId") String userId) {
        if (groupId != null && !groupId.isEmpty()) {

            Group group = GroupService.groupDb.get(groupId);
            if (group != null) {
                return group.getMessages();
            }
        } else if (userId != null && !userId.isEmpty()) {

            Collection<Message> result = new ArrayList<>();
            for (Message message : messageDb.values()) {
                if ((message.getSender() != null && userId.equals(message.getSender().getUserid())) ||
                        (message.getReceiver() != null && userId.equals(message.getReceiver().getUserid()))) {
                    result.add(message);
                }
            }
            return result;
        }
        return emptyList();
    }

    @GET
    @Path("{messageid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Message getMessageById(@PathParam("messageid") Long messageId) {

        Message message = messageDb.get(messageId);

        if(message == null)
            throw new IllegalStateException("User nicht gefunden");
        return message;
    }
    @DELETE
    @Path("{messageid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Message delete(@PathParam("messageid") Long messageId) {

        Message message = messageDb.remove(messageId);

        if(message == null)
            throw new IllegalStateException("User gelöscht");
        return message;
    }
    //TODO was mache ich mit get all
  /*  @GET
    // bracuht kein path mit messages , weil ist schon von messages gekommen.
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Message> getAllMessage()  {
        return messageDb.values();
    }*/


}
