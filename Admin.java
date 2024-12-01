package Entity;

public class Admin extends User {
    private int workingHours;
    private Role role;

    public Admin(String username, String password, java.util.Date dateOfBirth, Role role, int workingHours) {
        super(username, password, role, dateOfBirth);
        setRole(role); 
        setWorkingHours(workingHours); 
    }

    @Override
    public String toString() {
        return "Role: " + role + "\nWorking Hours: " + workingHours;
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

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        this.role = role;
    }
}
