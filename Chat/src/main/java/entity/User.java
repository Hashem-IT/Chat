package entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @XmlAttribute
    private String userid;
    private String name;

    //TODO mit List<Group> ist * von Group Richtung! bedeutet diese User hat viel Groups
    // das ist die rechte Relation in Diagramm
    //private List<Group> group1;

    public User() { }
    public User(String userid, String name) {
        this.userid = userid;
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

/*    public void setgroup(Group group1) {
        this.group1= (List<Group>) group1;
    }*/



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userid.equals(user.userid)) return false;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}