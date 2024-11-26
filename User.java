public abstract class User extends Entity {
    protected String username;
    protected String password;
    protected java.util.Date dateOfBirth;

    // Default username and password are "default"

    public User(){
        this.username = "default";
        this.password = "default";
        this.dateOfBirth = new java.util.Date();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.dateOfBirth = new java.util.Date();
    }

    public User(String username, String password, java.util.Date date){
        this.username = username;
        this.password = password;
        this.dateOfBirth = new java.util.Date(date.getTime());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username, Database db, Admin admin) {
        Table userList = db.get(admin, "users");

        for (int i = 0; i < userList.getNumberOfElements(); i++) {
            if(username.equals(((User)userList.get(i)).username)) {
                throw new AlreadyExists("Username already exists");
            }
        }

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

    public void save(Database db, Admin admin){
        if(this.isSaved()){
            throw new AlreadySaved("User already saved");
        }

        else{
            super.save(db, admin, "users");
        }
    }

    public void delete(Database db, Admin admin){
        super.delete(db, admin, "users");
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
