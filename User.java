package Entity;
import java.util.Date;
public abstract class User implements Entity {
    protected String username;
    protected String password;
    protected Role role;
    protected java.util.Date dateOfBirth;

    public User(String username, String password,Role role ,java.util.Date dateOfBirth){
        setUsername(username);
        setPassword(password);
        setRole(role);
        setDateOfBirth(dateOfBirth);
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
       }
        this.role = role;
    }
    
    public void setUsername(String username) {
         if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (username.length() < 3 || username.length() > 20) { 
            throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
        }
        this.username = username;
    }

  

    public void setPassword(String password) {
         if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (password.length() < 8) { 
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }

    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
         if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        Date today = new Date();
        if (dateOfBirth.after(today)) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        this.dateOfBirth = dateOfBirth;
    }

}


