package entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Collection;


import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {

    @XmlAttribute
    private String groupid;
    private String name;
    //TODO relation von 1 Group zu viel members
    private Collection<User> members;
    //TODO relation von 1 Group zu viel messages
    private Collection<Message> messages;
    private User owner;
    @XmlTransient
    private Message message;

    public Group() { }

    public Group(String groupid, String name) {
        this.groupid = groupid;    this.name = name;    }

    public Group(String groupid, String name, Collection<User> members, User owner, Collection<Message> messages) {
        this.groupid = groupid;
        this.name = name;
        this.members = members;
        this.owner = owner;
        this.messages = messages;
    }

    public String getGroupid() { return groupid; }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Collection<Message> getMessage() {
        return messages;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    public User getOwner() {
        return owner;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupid='" + groupid + '\'' +
                ", name='" + name + '\'' +
                '}';
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
