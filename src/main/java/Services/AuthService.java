package Services;
import java.util.ArrayList;
import java.util.HashMap;

import Entity.*;
public class AuthService{
    private User loggedInUser;

    public AuthService(){
        
    }

    public boolean Login(String username, String password){
        if (loggedInUser!=null) {
            return false;
        }

        if (username==null || password==null) {
            return false;
        }
        EntityDAO<Customer> customerDAO = new EntityDAO<>("customers", Customer.class);
        EntityDAO<Admin> adminDAO = new EntityDAO<>("admins", Admin.class);

        int i = customerDAO.getIndex(username);
        if (i !=-1) {
            if (customerDAO.getAll().get(i).checkPassword(password)) {
                loggedInUser = new Customer(customerDAO.getAll().get(i));
                return true;
            }else{
                return false;
            }
        }

        i = adminDAO.getIndex(username);
        if (i !=-1) {
            if (adminDAO.getAll().get(i).checkPassword(password)) {
                loggedInUser = new Admin(adminDAO.getAll().get(i));
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

        EntityDAO<Customer> customerDAO = new EntityDAO<>("customers", Customer.class);
        EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);

        if (customerDAO.getIndex(username)!=-1) {
            return false;
        }

        try {
            String cartId = orderDAO.nextId();
            Customer user = new Customer(username,password,dateOfBirth,balance,address,gender,new ArrayList<>(),cartId);
            Order cart = new Order(cartId, username, new HashMap<>(), balance, null, Status.draft);
            customerDAO.add(user);
            orderDAO.add(cart);
            loggedInUser = user;
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
