package Services;

import java.util.ArrayList;
import java.util.Comparator;
import Entity.Message;


public class ChatService extends EntityService<Message>{
    private PermissionService permission;
    private AdminService adminService;
    private CustomerService customerService;

    public ChatService(AuthService authService, PermissionService permission, AdminService adminService, CustomerService customerService) {
        super("messages", Message.class, authService);
        this.permission = permission;
        this.adminService = adminService;
        this.customerService = customerService;
    }

    public String create(String from, String to, String content) {
        if (permission.hasPermission("messages", "create")) {
            if (customerService.get(from) == null && adminService.get(from) == null){
                throw new IllegalArgumentException("This sender doesn't exist");
            }

            if (customerService.get(to) == null && adminService.get(to) == null){
                throw new IllegalArgumentException("This receiver doesn't exist");
            }

            String id = getEntityDAO().nextId();
            getEntityDAO().add(new Message(id, from, to, content));
            return id;
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String id) {
        if (permission.hasPermission("messages", "delete")) {
            getEntityDAO().delete(id);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Message get(String id){
        if (permission.hasPermission("messages","retrieve")) {
            Message message = getEntityDAO().get(id);
            if (message == null) {
                throw null;  
            }
            return new Message(message);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Message> filter(String from, String to){
        if (permission.hasPermission("messages","retrieve")) {
            if (from==null && to==null) {
                throw new IllegalArgumentException("Sender and Reciever can't be null.");
            }
            if (customerService.get(from) == null && adminService.get(from) == null){
                throw new IllegalArgumentException("This sender doesn't exist");
            }

            if (customerService.get(to) == null && adminService.get(to) == null){
                throw new IllegalArgumentException("This receiver doesn't exist");
            }

            ArrayList<Message> messages = new ArrayList<>();

            for (Message message : getEntityDAO().getAll()) {
                if (from==null && message.getTo().equals(to)) {
                    messages.add(message);
                }else if(message.getFrom().equals(from) && to==null){
                    messages.add(message);
                }
                else if (message.getFrom().equals(from) && message.getTo().equals(to)) {
                    messages.add(message);
                }
            }
            return messages;

        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Message> getChat(String from, String to){
        if (permission.hasPermission("messages","retrieve")) {
            if (customerService.get(from) == null && adminService.get(from) == null){
                throw new IllegalArgumentException("This sender doesn't exist");
            }

            if (customerService.get(to) == null && adminService.get(to) == null){
                throw new IllegalArgumentException("This receiver doesn't exist");
            }

            if (from==null || to==null) {
                throw new IllegalArgumentException("Sender or Reciever can't be null.");
            }

            ArrayList<Message> chat = new ArrayList<>(filter(from, to));
            chat.addAll(filter(to, from));
            chat.sort(Comparator.comparing(Message::getDate));
            return chat;
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Message> getAll(){
        if (permission.hasPermission("messages","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String id,String parameter ,T newData) {
        if (permission.hasPermission("messages", "update")) {
            Message message = getEntityDAO().get(id);
            if (message == null) {
                throw new IllegalArgumentException("This message doesn't exist.");  
            }

            switch (parameter.toLowerCase()) {
                case "content":
                    message.setContent((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            getEntityDAO().update(message);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
    
}
