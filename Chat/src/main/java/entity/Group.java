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
    // nur name von User  Collection<String> wegen <FK>
    private Collection<String> members;
    private String owner ;

    //TODO relation von 1 Group zu viel messages
    private Collection<Message> messages;
    public Group() { }
    public Group(String groupid, String name, String owner, Collection<String> members) {
        this.groupid = groupid;
        this.name = name;
        this.owner = owner;
        this.members = members;
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

    public Collection<String> getMembers() {
        return members;
    }

    public void setMembers(Collection<String> members) {
        this.members = members;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
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
