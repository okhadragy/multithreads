package Services;

import Entity.*;
import java.util.*;

import Database.EntityDAO;


public class PermissionService extends EntityService<Permisson>{
    private final EntityDAO<Permisson> entityDAO;

    public PermissionService() {
        super("permissons", Permisson.class);
        entityDAO = super.entityDAO;
    }

    public String getServiceActionName(String tableName, String action){
        return tableName.toUpperCase()+"_"+action.toUpperCase();
    }

    public boolean hasPermission(Role role, String action) {
        if (role==null||action==null) {
            throw new RuntimeException("Data is not complete");
        }

        ArrayList<String> allowedActions = entityDAO.get(role).getActions();
        if (allowedActions == null) return false;

        for (String allowedAction : allowedActions) {
            if (allowedAction.equals(action)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermission(Role role,String tableName ,String action) {
        if (tableName==null) {
            throw new RuntimeException("Data is not complete");
        }

        action = getServiceActionName(tableName, action);
        return hasPermission(role, action);
    }

    public void addPermisson(User loggedInUser, Role role, ArrayList<String> actions) throws RuntimeException{
        if (loggedInUser==null||role==null||actions==null) {
            throw new RuntimeException("Data is not complete");
        }

        if (loggedInUser.getRole().equals(Role.superadmin)) {
            entityDAO.add(new Permisson(role, actions));
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void deletePermisson(User loggedInUser, Role role) throws RuntimeException {
        if (loggedInUser==null||role==null) {
            throw new RuntimeException("Data is not complete");
        }

        if (loggedInUser.getRole().equals(Role.superadmin)) {
            entityDAO.delete(role);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }

    }

    public void addAction(User loggedInUser, Role role, String action) throws RuntimeException{
        if (loggedInUser==null||role==null||action==null) {
            throw new RuntimeException("Data is not complete");
        }
        
        if (loggedInUser.getRole().equals(Role.superadmin)) {
            Permisson permisson = entityDAO.get(role);
            permisson.getActions().add(action);
            entityDAO.update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addAction(User loggedInUser, Role role, String tableName ,String action) throws RuntimeException{
        if (tableName==null) {
            throw new RuntimeException("Data is not complete");
        }
        addAction(loggedInUser, role, getServiceActionName(tableName, action));
    }

    public void addActions(User loggedInUser, Role role, ArrayList<String> actions) throws RuntimeException{
        if (loggedInUser==null||role==null||actions==null) {
            throw new RuntimeException("Data is not complete");
        }
        
        if (loggedInUser.getRole().equals(Role.superadmin)) {
            Permisson permisson = entityDAO.get(role);
            permisson.setActions(actions);
            entityDAO.update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void deleteAllActions(User loggedInUser, Role role) throws RuntimeException{
        addActions(loggedInUser, role, new ArrayList<>());
    }

    public void deleteAction(User loggedInUser, Role role, String action) throws RuntimeException {
        if (loggedInUser==null||role==null||action==null) {
            throw new RuntimeException("Data is not complete");
        }

        if (loggedInUser.getRole().equals(Role.superadmin)) {
            Permisson permisson = entityDAO.get(role);
            permisson.getActions().remove(action);
            entityDAO.update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }

    }

    public void deleteAction(User loggedInUser, Role role, String tableName ,String action) throws RuntimeException {
        if (tableName==null) {
            throw new RuntimeException("Data is not complete");
        }
        deleteAction(loggedInUser, role, getServiceActionName(tableName, action));
    }    
}
