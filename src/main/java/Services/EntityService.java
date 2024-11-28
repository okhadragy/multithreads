package Services;

import Entity.*;
import Database.*;

abstract public class EntityService<E extends Entity> {
    final EntityDAO<E> entityDAO;
    private final Class type;
    final String tableName;

    public EntityService(String tableName, Class type){
        this.type = type;
        this.tableName = tableName;
        entityDAO = new EntityDAO<>(tableName,type);
    }

}

