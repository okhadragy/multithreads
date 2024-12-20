package Services;

import java.util.ArrayList;

import Entity.Product;

public class ProductService extends EntityService<Product> {
    private PermissionService permission;
    private CategoryService categoryService;

    public ProductService(AuthService authService, PermissionService permission, CategoryService categoryService) {
        super("products", Product.class, authService);
        this.permission = permission;
        this.categoryService = categoryService;
    }

    public PermissionService getPermission() {
        return permission;
    }

    public void setPermission(PermissionService permission) {
        this.permission = permission;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String create(String name, String desc, String image, double price, String categoryId) {
        if (permission == null || !permission.hasPermission("products", "create")) {
            throw new RuntimeException("Permission denied: Unable to create product.");
        }

        if (categoryId != null && categoryService.get(categoryId) == null) {
            throw new IllegalArgumentException("Error: This category doesn't exist.");
        }

        String id = getEntityDAO().nextId();
        getEntityDAO().add(new Product(id, name, desc, image, price, categoryId));
        return id;
    }

    public void delete(String id) {
        if (permission == null || !permission.hasPermission("products", "delete")) {
            throw new RuntimeException("Permission denied: Unable to delete product.");
        }

        Product product = getEntityDAO().get(id);
        if (product == null) {
            throw new IllegalArgumentException("Error: Product not found.");
        }

        getEntityDAO().delete(id);
    }

    public Product get(String id) {
        if (permission == null || !permission.hasPermission("products", "retrieve")) {
            throw new RuntimeException("Permission denied: Unable to retrieve product.");
        }

        Product product = getEntityDAO().get(id);
        return product != null ? new Product(product) : null;
    }

    public ArrayList<Product> getAll() {
        if (permission == null || !permission.hasPermission("products", "retrieve")) {
            throw new RuntimeException("Permission denied: Unable to retrieve products.");
        }

        return getEntityDAO().getAll();
    }

    public <T> void update(String id, String parameter, T newData) {
        if (permission == null || !permission.hasPermission("products", "update")) {
            throw new RuntimeException("Permission denied: Unable to update product.");
        }

        Product product = getEntityDAO().get(id);
        if (product == null) {
            throw new IllegalArgumentException("Error: This product doesn't exist.");
        }

        switch (parameter.toLowerCase()) {
            case "name":
                product.setName((String) newData);
                break;
            case "quantity":
                product.setQuantity((int) newData);
                break;
            case "price":
                product.setPrice((double) newData);
                break;
            case "description":
                product.setDescription((String) newData);
                break;
            case "category":
                if (newData != null && categoryService.get((String) newData) == null) {
                    throw new IllegalArgumentException("Error: Category doesn't exist.");
                }
                product.setCategoryId((String) newData);
                break;
            case "image":
                product.setImage((String) newData);
                break;
            default:
                throw new IllegalArgumentException("Error: Invalid parameter for update.");
        }

        getEntityDAO().update(product);
    }
}
