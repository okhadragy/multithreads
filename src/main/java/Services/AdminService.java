package Services;
import java.util.ArrayList;

import Entity.*;

public class AdminService extends EntityService<Admin>{    
    private PermissionService permission;

    public AdminService(AuthService authService){
        super("admins", Admin.class, authService);
        permission = new PermissionService(authService);
    }

    public void create(String username, String password, java.util.Date dateOfBirth, Role role, int workingHours){
        if (permission.hasPermission("admins","create")) {
            if (getEntityDAO().getIndex(username)!=-1) {
                throw new IllegalArgumentException("This username is used");
            }
            
            getEntityDAO().add(new Admin(username, password, dateOfBirth, role, workingHours));
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String username){
        if (permission.hasPermission("admins","delete")) {
            Admin user = getEntityDAO().get(username);

            if (user.getRole().equals(Role.superadmin)) {
                for (Admin admin : getEntityDAO().getAll()) {
                    if(admin.getRole().equals(Role.superadmin)){
                        getEntityDAO().delete(username);
                        return;
                    }

                }
                throw new RuntimeException("You can't delete the only super admin");
            }

            if (getLoggedInUser().equals(user)) {
                getAuthService().Logout();
            }

            getEntityDAO().delete(username);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Admin> getAll(){
        if (permission.hasPermission("admins","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Admin get(String username){
        if (permission.hasPermission("admins","retrieve")||getLoggedInUser().equals(getEntityDAO().get(username))) {
            return getEntityDAO().get(username);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String username,String parameter ,T newData){
        if (permission.hasPermission("admins","update")) {
            Admin user = getEntityDAO().get(username);
            switch (parameter.toLowerCase()) {
                case "password":
                    user.setPassword((String)newData);
                    break;
                case "workinghours":
                    user.setWorkingHours((int)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            getEntityDAO().update(user);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
