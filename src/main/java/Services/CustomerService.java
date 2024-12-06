package Services;
import java.util.ArrayList;

import Entity.*;


public class CustomerService extends EntityService<Customer> {
    private PermissionService permission;
    private final EntityDAO<Order> orderDAO;

    public CustomerService(AuthService authService) {
        super("customers", Customer.class, authService);
        permission = new PermissionService(authService);
        orderDAO = new EntityDAO<>("orders", Order.class);
    }

    public void create(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender) {        
        if (permission.hasPermission("customers", "create")) {
            if (getEntityDAO().getIndex(username)!=-1) {
                throw new IllegalArgumentException("This username is used");
            }

            String cartId = orderDAO.nextId();
            Order cart = new Order(cartId, username, new ArrayList<>(), balance, null, Status.draft);
            Customer user = new Customer(username,password,dateOfBirth,balance,address,gender,new ArrayList<>(),cartId);    
            getEntityDAO().add(user);
            orderDAO.add(cart);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String username) {
        if (permission.hasPermission("customers", "delete")) {
            Customer customer = getEntityDAO().get(username);
            this.getEntityDAO().delete(username);
            orderDAO.delete(customer.getCartId());
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Customer get(String username){
        if (permission.hasPermission("customers","retrieve")|| getLoggedInUser().equals(getEntityDAO().get(username))) {
            return getEntityDAO().get(username);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Customer> getAll(){
        if (permission.hasPermission("customers","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String username,String parameter ,T newData) {
        if (permission.hasPermission("customers", "update")) {
            Customer customer = getEntityDAO().get(username);
            switch (parameter.toLowerCase()) {
                case "password":
                    customer.setPassword((String)newData);
                    break;
                case "balance":
                    customer.setBalance((double)newData);
                    break;
                case "address":
                    customer.setAddress((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }


}
