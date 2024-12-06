package Services;
import java.util.ArrayList;

import Entity.*;
public class AuthService{
    private final EntityDAO<Customer> customerDAO;
    private final EntityDAO<Admin> adminDAO;
    private final EntityDAO<Order> orderDAO;
    private User loggedInUser;

    public AuthService(){
        customerDAO = new EntityDAO<>("customers", Customer.class);
        adminDAO = new EntityDAO<>("admins", Admin.class);
        orderDAO = new EntityDAO<>("orders", Order.class);
    }

    public boolean Login(String username, String password){
        if (loggedInUser!=null) {
            return false;
        }

        if (username==null || password==null) {
            return false;
        }

        int i = customerDAO.getIndex(username);
        if (i !=-1) {
            if (customerDAO.getAll().get(i).checkPassword(password)) {
                loggedInUser = customerDAO.getAll().get(i);
                return true;
            }else{
                return false;
            }
        }

        i = adminDAO.getIndex(username);
        if (i !=-1) {
            if (adminDAO.getAll().get(i).checkPassword(password)) {
                loggedInUser = adminDAO.getAll().get(i);
                return true;
            }else{
                return false;
            }
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

        if (customerDAO.getIndex(username)!=-1) {
            return false;
        }

        try {
            String cartId = orderDAO.nextId();
            Order cart = new Order(cartId, username, new ArrayList<>(), balance, null, Status.draft);
            Customer user = new Customer(username,password,dateOfBirth,balance,address,gender,new ArrayList<>(),cartId);    
            customerDAO.add(user);
            orderDAO.add(cart);
            loggedInUser = user;
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
