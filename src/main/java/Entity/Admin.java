package Entity;

import java.net.InetAddress;
import java.util.ArrayList;

public class Admin extends User {
    private int workingHours;

    public Admin(String username, String password, Role role, java.util.Date dateOfBirth, int workingHours) {
        super(username, password, role, dateOfBirth);
        setWorkingHours(workingHours);
    }

    public Admin(Admin admin) {
        this(admin.getUsername(), admin.getPassword(), admin.getRole(), admin.getDateOfBirth(), admin.workingHours);  
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours < 0 || workingHours > 24) {
            throw new IllegalArgumentException("Working hours must be between 0 and 24.");
        }
        this.workingHours = workingHours;
    }
    
    @Override
    public String toString() {
        return super.toString()+"\nRole: " + getRole() + "\nWorking Hours: " + workingHours;
    }
}
