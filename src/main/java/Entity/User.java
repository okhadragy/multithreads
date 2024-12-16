package Entity;
import java.net.*;
import java.util.ArrayList;

public abstract class User implements Entity {
    private String username;
    private String password;
    private Role role;
    private java.util.Date dateOfBirth;
    private ArrayList<InetAddress> hostAddresses;

    public User(String username, String password,Role role,java.util.Date dateOfBirth, ArrayList<InetAddress> hostAddresses){
        setUsername(username);
        setPassword(password);
        setRole(role);
        setDateOfBirth(dateOfBirth);
        setHostAddresses(hostAddresses);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
           throw new IllegalArgumentException("Username cannot be null or empty.");
       }
       if (username.length() < 3 || username.length() > 20) { 
           throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
       }
       this.username = username.toLowerCase();
    }

    public boolean checkPassword(String password){
        return (this.password).equals(password);
    }

    protected String getPassword() {
        return password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
       }
        this.role = role;
    }
    
    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        
        java.util.Date today = new java.util.Date();
        if (dateOfBirth.after(today)) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<InetAddress> getHostAddresses() {
        if (hostAddresses == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(hostAddresses);
    }

    public void setHostAddresses(ArrayList<InetAddress> hostAddresses) {
        if (hostAddresses == null) {
            throw new IllegalArgumentException("Host Addresses cannot be null.");
        }

        if (hostAddresses.isEmpty()) {
            throw new IllegalArgumentException("Host Addresses cannot be empty.");
        }

        for (InetAddress hostAddress : hostAddresses) {
            if (hostAddress == null) {
                throw new IllegalArgumentException("Host Address cannot be null.");
            }
        }

        this.hostAddresses = hostAddresses;
    }

    public void addHostAddress(InetAddress hostAddress){
        if (hostAddress == null) {
            throw new IllegalArgumentException("Host Address cannot be null.");
        }

        if (hostAddresses.contains(hostAddress)) {
            return;
        }

        hostAddresses.add(hostAddress);
    }
    
    public void removeHostAddress(InetAddress hostAddress){
        if (hostAddress == null) {
            throw new IllegalArgumentException("Host Address cannot be null.");
        }
        if (!hostAddresses.contains(hostAddress)) {
            throw new IllegalArgumentException("This Host Address is not in your addresses.");
        }

        hostAddresses.remove(hostAddress);
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
