package Services;
import java.util.*;

import Entity.*;


public class CustomerService extends EntityService<Customer> {
    private PermissionService permission;
    private final EntityDAO<Order> orderDAO;
    private final EntityDAO<Product> productDAO;

    public CustomerService(AuthService authService) {
        super("customers", Customer.class, authService);
        permission = new PermissionService(authService);
        orderDAO = new EntityDAO<>("orders", Order.class);
        productDAO = new EntityDAO<>("products", Product.class);
    }

    public void create(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender) {        
        if (permission.hasPermission("customers", "create")) {
            if (getEntityDAO().getIndex(username)!=-1) {
                throw new IllegalArgumentException("This username is used");
            }

            String cartId = orderDAO.nextId();
            Order cart = new Order(cartId, username, new HashMap<>(), balance, null, Status.draft);
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
            return new Customer(getEntityDAO().get(username));
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
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))) {
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

    public void addToCart(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Product product = productDAO.get(productId);
            Customer customer = getEntityDAO().get(username);
            Order cart = orderDAO.get(customer.getCartId());
            cart.addProduct(productId);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeFromCart(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Product product = productDAO.get(productId);
            Customer customer = getEntityDAO().get(username);
            Order cart = orderDAO.get(customer.getCartId());
            cart.removeProduct(productId);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Map<String, Integer> getCartProducts(String username){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Customer customer = getEntityDAO().get(username);
            Order cart = orderDAO.get(customer.getCartId());
            return cart.getProducts();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Product product = productDAO.get(productId);
            Customer customer = getEntityDAO().get(username);
            ArrayList<String> products = customer.getInterests();
            products.add(productId);
            customer.setInterests(products);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Product product = productDAO.get(productId);
            Customer customer = getEntityDAO().get(username);
            ArrayList<String> products = customer.getInterests();
            products.remove(productId);
            customer.setInterests(products);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
