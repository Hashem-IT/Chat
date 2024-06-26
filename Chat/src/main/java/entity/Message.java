package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message  {
       @XmlAttribute
    private Long messageid;
    private String text;
    private Date timestamp;

    private User sender;
    private User receiver;

    public Message() {}

    public Message(Long messageid, String text, Date timestamp, User sender, User receiver) {
        this.messageid = messageid;
        this.text = text;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getMessageid() {
        return messageid;
    }
    public void setMessageid(Long messageid) {
        this.messageid = messageid;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageid=" + messageid +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!messageid.equals(message.messageid)) return false;
        if (!text.equals(message.text)) return false;
        return timestamp.equals(message.timestamp);
    }
    @Override
    public int hashCode() {
        int result = messageid.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}
