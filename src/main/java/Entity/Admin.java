package Entity;

public class Admin extends User {
    private int workingHours;
    private Role role;

    public Admin(String username, String password,java.util.Date dateOfBirth, Role role, int workingHours){
        super(username, password, role, dateOfBirth);
        this.role = role;
        this.workingHours = workingHours;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Role: " + role + "\nWorking Hours: " + workingHours;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

}
