package Services;

import Entity.*;
import Database.*;

public class EntityService<E extends Entity> {
    protected final EntityDAO<E> entityDAO;
    protected final PermissionService permissionService;
    private final Class type;

    public EntityService(String tableName, Class type){
        this.type = type;
        entityDAO = new EntityDAO<>(tableName,type);
        permissionService = new PermissionService();
    }

}

class ProductService extends EntityService<Product>{

    public ProductService(){
        super("products", Product.class);
        entityDAO.add(null);
    }

}
