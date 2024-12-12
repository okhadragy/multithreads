package Entity;

import java.util.*;

public class Message implements Entity {
    private String id;
    private String from;
    private String to;
    private String content;
    private Date date;

    public Message(String id, String from, String to, String content){
        setId(id);
        setFrom(from);
        setTo(to);
        setContent(content);
        this.date = new java.util.Date();
    }

    public Message(Message message){
        this(message.id, message.from, message.to, message.content);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        if (from == null || from.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender ID cannot be null or empty.");
        }
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver ID cannot be null or empty.");
        }

        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Message content can't be null.");
        }
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        
        java.util.Date today = new java.util.Date();
        if (date.after(today)) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        this.date = date;
    }
    

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return from+": " + content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            return this.id == ((Message) obj).id;
        }
        return false;
    }

}
