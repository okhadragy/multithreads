package Services;

import java.net.InetAddress;
import java.util.ArrayList;

import Entity.*;

public class AuthService {
    private User loggedInUser;
    private CustomerService customerService;
    private AdminService adminService;

    public AuthService(CustomerService customerService, AdminService adminService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean Login(String username, String password) {
        if (loggedInUser != null) {
            return false;
        }

        if (username == null || password == null) {
            return false;
        }

        username = username.toLowerCase();
        loggedInUser = customerService.get(username);

        if (loggedInUser != null) {
            if (loggedInUser.checkPassword(password)) {
                return true;
            } else {
                loggedInUser = null;
                return false;
            }
        }

        loggedInUser = adminService.get(username);
        if (loggedInUser != null) {
            if (loggedInUser.checkPassword(password)) {
                return true;
            } else {
                loggedInUser = null;
                return false;
            }
        }

        return false;
    }

    public void Logout() {
        loggedInUser = null;
    }

    public void Signup(String username, String password, java.util.Date dateOfBirth, String address, Gender gender){
        if (loggedInUser != null) {
            throw new RuntimeException("can't sign up while you are logged in");
        }

        customerService.create(username, password, dateOfBirth, 1000, address, gender);
        loggedInUser = customerService.get(username);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
