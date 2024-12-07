package Services;

import java.util.ArrayList;

import Entity.*;

public class ProductService extends EntityService<Product> {
    private PermissionService permission;

    public ProductService(AuthService authService){
        super("products",Product.class, authService);
        permission = new PermissionService(authService);
    }

    public void create(String name, String desc, String image, double price, String categoryId) {
        if (permission.hasPermission("products", "create")) {
            getEntityDAO().add(new Product(getEntityDAO().nextId(), name, desc, image, price, categoryId));
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void create(String name, String desc, String image, double price, String categoryId, int quantity) {
        if (permission.hasPermission("products", "create")) {
            getEntityDAO().add(new Product(getEntityDAO().nextId(), name, desc, image, price, categoryId, quantity));
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String id) {
        if (permission.hasPermission("products", "delete")) {
            getEntityDAO().delete(id);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Product get(String id){
        if (permission.hasPermission("products","retrieve")) {
            return new Product(getEntityDAO().get(id));
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Product> getAll(){
        if (permission.hasPermission("products","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String id,String parameter ,T newData) {
        if (permission.hasPermission("products", "update")) {
            Product product = getEntityDAO().get(id);
            switch (parameter.toLowerCase()) {
                case "name":
                    product.setName((String)newData);
                    break;
                case "quantity":
                    product.setQuantity((int)newData);
                    break;
                case "price":
                    product.setPrice((double)newData);
                    break;
                case "description":
                    product.setDescription((String)newData);
                    break;
                case "category":
                    product.setCategoryId((String)newData);
                    break;
                case "image":
                    product.setImage((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(product);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
