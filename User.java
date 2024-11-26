public abstract class User extends Entity {
    protected String username;
    protected String password;
    protected java.util.Date dateOfBirth;

    // CONSTURCTORS +User()
    // and +User(username: String, password: String)
    // and +User(username: String, password: String, date: java.util.Date)

    public String getUsername() {
        return username;
    }

    public void setUsername(String username, Database db, Admin admin) {
        Table<User> userList = db.get(admin, "users");

        for (int i = 0; i < userList.getnumberOfElements(); i++) {
            if(username.equals((userList.get(i)).username)) {
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
