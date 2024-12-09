package Services;

import java.util.*;

import Entity.*;

public class OrderService extends EntityService<Order> {
    private final PermissionService permission;
    private final ProductService productService;

    public OrderService(AuthService authService, PermissionService permission, ProductService productService) {
        super("orders", Order.class, authService);
        this.permission = permission;
        this.productService = productService;
    }

    public double calcTotal(Map<String, Integer> products) {
        Product product;
        double Total=0;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            product = productService.get(entry.getKey());
            Total += (product.getPrice() * entry.getValue());
        }
        return Total;
    }

    public void addItem(String OrderID, String ProductID) {
        if (permission.hasPermission("orders", "update")) {
            Order order = getEntityDAO().get(OrderID);
            order.addProduct(ProductID);
            order.setProducts(order.getProducts());
            order.setTotal(calcTotal(order.getProducts()));
            getEntityDAO().update(order);
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void deleteItem(String OrderID, String ProductID) {
        if (permission.hasPermission("orders", "update")) {
            Order order = getEntityDAO().get(OrderID);
            order.removeProduct(ProductID);
            order.setProducts(order.getProducts());
            order.setTotal(calcTotal(order.getProducts()));
            getEntityDAO().update(order);
            
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void create(String customer, Map<String,Integer> products, PaymentMethod paymentMethod, Status status) {
        if (permission.hasPermission("orders", "create")) {
            EntityDAO<Customer> customerDAO = new EntityDAO<>("customers", Customer.class);
            customerDAO.get(customer);
            getEntityDAO().add(new Order(getEntityDAO().nextId(), customer, products, calcTotal(products), paymentMethod, status));
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String OrderID) {
        if (permission.hasPermission("orders", "delete")) {
            getEntityDAO().delete(OrderID);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Order get(String OrderID){
        if (permission.hasPermission("orders","retrieve")) {
            return new Order(getEntityDAO().get(OrderID));
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Order> getAll(){
        if (permission.hasPermission("orders","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void convertToOrder(String OrderID) {
        Order cart = getEntityDAO().get(OrderID);
        if (permission.hasPermission("orders", "update")|| cart.getCustomer().equals(getLoggedInUser().getUsername())) {
            if (cart.getStatus().equals(Status.draft)) {
                Order order = new Order(cart);
                System.out.println(order.getProducts());
                System.out.println(cart.getProducts());
                cart.setProducts(new HashMap<>());
                order.setId(getEntityDAO().nextId());
                order.setStatus(Status.processing);
                getEntityDAO().update(cart);
                getEntityDAO().add(order);
            }else{
                throw new RuntimeException("This is already an order.");
            }
            
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void pay(String OrderID, PaymentMethod payMeth) throws RuntimeException {
        Order order = getEntityDAO().get(OrderID);
        if (permission.hasPermission("orders", "update") || order.getCustomer().equals(getLoggedInUser().getUsername())) {
            EntityDAO<Customer> customerDAO = new EntityDAO<>("customers", Customer.class);
            Customer customer = customerDAO.get(order.getCustomer());
            if (!order.getProducts().isEmpty()) {
                if (order.getStatus().equals(Status.processing)) {
                    order.setPaymentMethod(payMeth);
                    customer.setBalance(customer.getBalance() - order.getTotal());
                    order.setStatus(Status.shipping);
                    customerDAO.update(customer);
                    getEntityDAO().update(order);
                }
                else
                    throw new RuntimeException("Can't pay for this order");
            } else
                throw new RuntimeException("the order doesn't have any products.");
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void deliver(String OrderID) {
        Order order = getEntityDAO().get(OrderID);
        if (permission.hasPermission("orders", "update")|| order.getCustomer().equals(getLoggedInUser().getUsername())) {
            if (order.getStatus().equals(Status.shipping)) {
                order.setStatus(Status.delivered);
                getEntityDAO().update(order);
            }else{
                throw new RuntimeException("This order hasn't been shipped.");
            }
            
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void close(String OrderID) {
        Order order = getEntityDAO().get(OrderID);
        if (permission.hasPermission("orders", "update")|| order.getCustomer().equals(getLoggedInUser().getUsername())) {
            if (order.getStatus().equals(Status.delivered)) {
                order.setStatus(Status.closed);
                getEntityDAO().update(order);
            }else{
                throw new RuntimeException("This order hasn't been delivered.");
            }
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void cancel(String OrderID) {
        Order order = getEntityDAO().get(OrderID);
        if (permission.hasPermission("orders", "update")|| order.getCustomer().equals(getLoggedInUser().getUsername())) {
            if (!order.getStatus().equals(Status.closed)) {
                order.setStatus(Status.cancelled);
                getEntityDAO().update(order);
            }else{
                throw new RuntimeException("You can't cancel closed order.");
            }
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

}
