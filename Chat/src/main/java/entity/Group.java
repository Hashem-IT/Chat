package entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {

    @XmlAttribute
    private String groupid;
    private String name;

    //TODO mit List<User> ist * von User Richtung! bedeutet diese Groupe hat viel Users
    // das ist die rechte Relation in Diagramm


    //private User owner;

    //private List<Message> messages;

    //TODO das in diagramm create in beziehung 1 zu 1
   // Message message = new Message();
    //private List<Message> messagess;

    public Group() { }

    public Group(String groupid, String name) {
        this.groupid = groupid;
        this.name = name;
    }

    private Collection<User> user;
    public Group(String groupid, String name, Collection<User> user) {
        this.groupid = groupid;
        this.name = name;
        this.user = user;
    }

    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return user;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!groupid.equals(group.groupid)) return false;
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        int result = groupid.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
