package service;

import entity.Group;
import entity.Message;
import entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    @GET
    @Path("all")
    public Collection<Message> getAllMessage() throws ParseException {


        // Message nur
        Collection<Message> Message1 = new ArrayList<>();
        // Datum-String
        String date1 = "2001-01-01";
        String date2 = "2002-02-02";

        // Datum parsen
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS1 = dateFormat.parse(date1);
        Date dateS2 = dateFormat.parse(date2);


        Message message1 =new Message(1L,"Message Test 1",dateS1);
        Message message2 =new Message(2L,"Message Test 2",dateS2);

        messageDb.put(1,message1);
        messageDb.put(2,message2);

        Message1.add(message1);
        Message1.add(message2);

        // Message mit Group
        Collection<Group> GroupL = new ArrayList<>();
        Collection<User> UserL1 = new ArrayList<>();
        Collection<User> UserL2 = new ArrayList<>();

        User ul1 =new User("5","ters");
        User ul2 =new User("6","fans");
        User ul3 =new User("7","mrae");
        User ul4 =new User("8","torm");
        UserL1.add(ul1);
        UserL1.add(ul2);
        UserL2.add(ul3);
        UserL2.add(ul4);
        UserService.userDb.put(5,ul1);
        UserService.userDb.put(6,ul2);
        UserService.userDb.put(7,ul3);
        UserService.userDb.put(8,ul4);

        Group gU1 =new Group("5","Group 5",UserL1);
        Group gU2 =new Group("6","Group 6",UserL2);
        GroupService.groupDb.put(5,gU1);
        GroupService.groupDb.put(6,gU2);

        GroupL.add(gU1);
        GroupL.add(gU2);

        Message message3 = new Message(3L,"message 3",dateS1,GroupL);
        messageDb.put(3,message3);

        return messageDb.values();

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
