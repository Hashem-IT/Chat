package service;

import entity.Message;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("messages")
public class MessageService {
    public static ConcurrentMap<Integer, Message> messageDb = new ConcurrentHashMap<>();

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Message Creat(Message message){

        //TODO Math.toIntExact wandelt long zu int?
        messageDb.put(Math.toIntExact(message.getMessageid()),message);
        if (message != null){
            System.out.println("message eingefügt");
            return message;

        }else { throw new IllegalStateException("Message Fehler");}
    }

    @GET
    @Path("{messageid}")
    public Message getMessageById(@PathParam("messageid") int MessageId) {

        Message message = messageDb.get(MessageId);

        if(MessageId == message.getMessageid())
            return message;
        else
            throw new IllegalStateException("Es gibt kein student mit diese studentId");
    }

    @DELETE
    @Path("{messageid}")
    public Message delete(@PathParam("messageid") int MessageId) {

        Message message = messageDb.remove(MessageId);

        if(messageDb.get(MessageId) == null)
            throw new IllegalStateException("User gelöscht");
        return message;

    }

}
