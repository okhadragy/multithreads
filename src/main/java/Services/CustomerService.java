package Services;
import java.util.*;

import Entity.*;


public class CustomerService extends EntityService<Customer> {
    private final PermissionService permission;
    private final ProductService productService;

    public CustomerService(AuthService authService, PermissionService permission, ProductService productService) {
        super("customers", Customer.class, authService);
        this.permission = permission;
        this.productService = productService;
    }

    public void create(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender) {        
        if (permission.hasPermission("customers", "create")) {
            if (getEntityDAO().getIndex(username)!=-1) {
                throw new IllegalArgumentException("This username is used");
            }

            EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);
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
            EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);
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
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);
            Order cart = orderDAO.get(customer.getCartId());
            cart.addProduct(productId);
            orderDAO.update(cart);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeFromCart(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);
            Order cart = orderDAO.get(customer.getCartId());
            cart.removeProduct(productId);
            orderDAO.update(cart);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Map<String, Integer> getCartProducts(String username){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Customer customer = getEntityDAO().get(username);
            EntityDAO<Order> orderDAO = new EntityDAO<>("orders", Order.class);
            Order cart = orderDAO.get(customer.getCartId());
            return cart.getProducts();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            ArrayList<String> products = customer.getInterests();
            if (products.contains(productId)) {
                throw new RuntimeException("This product is already in interests");
            }
            products.add(productId);
            customer.setInterests(products);
            getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            ArrayList<String> products = customer.getInterests();
            products.remove(productId);
            customer.setInterests(products);
            getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
