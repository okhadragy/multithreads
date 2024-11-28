package Entity;

public abstract class User implements Entity {
    protected String username;
    protected String password;
    private Role role;
    protected java.util.Date dateOfBirth;

    public User(String username, String password,Role role ,java.util.Date dateOfBirth){
        this.username = username;
        this.password = password;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password){
        return (this.password).equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Object getKey() {
        return this.username;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nDate of Birth: " + dateOfBirth;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return this.username == ((User)obj).username;
        }
        return false;
    }

}
