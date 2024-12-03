package Services;
import java.util.ArrayList;

import Entity.*;

public class AdminService extends EntityService<Admin>{    
    private PermissionService permission;

    public AdminService(AuthService authService){
        super("admins", Admin.class, authService);
        permission = new PermissionService(authService);
    }

    public void add(String username, String password, java.util.Date dateOfBirth, Role role, int workingHours){
        if (permission.hasPermission("admins","add")) {
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
        if (permission.hasPermission("admins","retrieve")) {
            return getEntityDAO().get(username);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void setPassword(String username, String password){
        if (permission.hasPermission("admins","update")) {
            Admin user = getEntityDAO().get(username);
            user.setPassword(password);
            getEntityDAO().update(user);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void setWorkingHours(String username, int workingHours){
        if (permission.hasPermission("admins","update")) {
            Admin user = getEntityDAO().get(username);
            user.setWorkingHours(workingHours);
            getEntityDAO().update(user);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

}
