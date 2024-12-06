package Entity;

public class Admin extends User {
    private int workingHours;
    private String password;

    public Admin(String username, String password, java.util.Date dateOfBirth, Role role, int workingHours) {
        super(username, password, role, dateOfBirth);
        this.password = password;
        setWorkingHours(workingHours);
    }

    public Admin(Admin admin) {
        this(admin.getUsername(), admin.password, admin.getDateOfBirth(), admin.getRole(), admin.workingHours);
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
