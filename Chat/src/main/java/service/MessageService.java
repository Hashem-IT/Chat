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

        // Datum-String
        String date1 = "2001-01-01";
        String date2 = "2002-02-02";

        // Datum parsen
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS1 = dateFormat.parse(date1);
        Date dateS2 = dateFormat.parse(date2);

        // Users einfügen
        Collection<User> users1 = new ArrayList<>();
        Collection<User> users2 = new ArrayList<>();
        User user1 = new User("1","Yassin");
        User user2 = new User("2","Lukas");
        User user3 = new User("3","Tom");
        User user4 = new User("4","Las");
        users1.add(user1);
        users1.add(user2);
        users2.add(user3);
        users2.add(user4);
        UserService.userDb.put(1,user1);
        UserService.userDb.put(2,user2);
        UserService.userDb.put(3,user3);
        UserService.userDb.put(4,user4);

        // nur Messages einfügen
        Collection<Message> messages1 = new ArrayList<>();
        Collection<Message> messages2 = new ArrayList<>();

        Message message1 = new Message(1l,"message 1",dateS1);
        Message message2 = new Message(2l,"message 2",dateS2);
        Message message3 = new Message(3l,"message 3",dateS1);
        Message message4 = new Message(4l,"message 4",dateS2);

        messageDb.put(1,message1);
        messageDb.put(2,message2);
        messageDb.put(3,message3);
        messageDb.put(4,message4);

        messages1.add(message1);
        messages1.add(message2);
        messages2.add(message3);
        messages2.add(message4);

        // Groups einfügen
        Collection<Group> groups1 = new ArrayList<>();
        Collection<Group> groups2 = new ArrayList<>();

        Group group1 = new Group("1","Groupe 1",users1,user1,messages1);
        Group group2 = new Group("2","Groupe 2",users2,user1,messages2);
        Group group3 = new Group("1","Groupe 1",users1,user2,messages1);
        Group group4 = new Group("2","Groupe 2",users2,user2,messages2);

        GroupService.groupDb.put(1,group1);
        GroupService.groupDb.put(2,group2);
        GroupService.groupDb.put(3,group3);
        GroupService.groupDb.put(4,group4);

        groups1.add(group1);
        groups1.add(group2);
        groups2.add(group3);
        groups2.add(group4);


        // Messages mit users und Groups einfügen
        Collection<Message> messages3 = new ArrayList<>();

        Message message5 = new Message(5l,"message 1",dateS1,groups1,user1);
        Message message6 = new Message(6l,"message 2",dateS2,groups2,user2);
        messageDb.put(5,message5);
        messageDb.put(6,message6);
        messages3.add(message5);
        messages3.add(message6);

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
