package Services;

import Entity.*;
import java.util.*;

public class PermissionService {
    private final Map<Role, String[]> permissions;

    public PermissionService() {
        permissions = new HashMap<>();
        permissions.put(Role.superadmin, new String[]{"ADD_USER", "VIEW_USERS", "DELETE_USER"});
        permissions.put(Role.customer, new String[]{"VIEW_USERS"});
    }

    public boolean hasPermission(Role role, String action) {
        if (role == null) {
            role = Role.customer;
        }
        String[] allowedActions = permissions.get(role);
        if (allowedActions == null) return false;

        for (String allowedAction : allowedActions) {
            if (allowedAction.equals(action)) {
                return true;
            }
        }
        return false;
    }
}
