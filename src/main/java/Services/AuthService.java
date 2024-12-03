package Services;
import java.util.ArrayList;

import Database.EntityDAO;
import Entity.*;

public class AuthService extends EntityService<User>{
    private final EntityDAO<User> entityDAO;
    private User loggedInUser;

    public AuthService(){
        super("users", User.class);
        entityDAO = super.entityDAO;
    }

    public boolean Login(String username, String password){
        if (loggedInUser!=null) {
            return false;
        }

        if (username==null || password==null) {
            return false;
        }

        int i = entityDAO.getIndex(username);
        if (i ==-1) {
            return false;
        }

        if (entityDAO.getAll().get(i).checkPassword(password)) {
            loggedInUser = entityDAO.getAll().get(i);
            return true;
        }
        return false;
    }

    public void Logout(){
        loggedInUser = null;
    }

    public boolean Signup(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender){
        if (loggedInUser!=null) {
            return false;
        }

        if (username==null || password==null || dateOfBirth==null || address ==null || gender==null) {
            return false;
        }
        
        // validate each data and add orderService
        User user = new Customer(username,password,dateOfBirth,balance,address,gender,new ArrayList<>(),"1");
        
        try {
            entityDAO.add(user);
        } catch (Exception e) {
            return false;
        }
        
        loggedInUser = user;
        return true;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
