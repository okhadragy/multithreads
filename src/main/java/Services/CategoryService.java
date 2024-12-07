package Services;

import java.util.ArrayList;

import Entity.*;

public class CategoryService extends EntityService<Category> {
    private PermissionService permission;

    public CategoryService(AuthService authService){
        super("categories",Category.class, authService);
        permission = new PermissionService(authService);
    }

    public void create(String name, String description) {
        if (permission.hasPermission("categories", "create")) {
            getEntityDAO().add(new Category(getEntityDAO().nextId(), name, description,new ArrayList<>()));
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String id) {
        if (permission.hasPermission("categories", "delete")) {
            getEntityDAO().delete(id);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Category get(String id){
        if (permission.hasPermission("categories","retrieve")) {
            return new Category(getEntityDAO().get(id));
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Category> getAll(){
        if (permission.hasPermission("categories","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String id,String parameter ,T newData) {
        if (permission.hasPermission("categories", "update")) {
            Category category = getEntityDAO().get(id);
            switch (parameter.toLowerCase()) {
                case "name":
                    category.setName((String)newData);
                    break;
                case "description":
                    category.setDescription((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(category);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
