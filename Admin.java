enum Role {
    superadmin(1),
    product(2),
    customer(3),
    order(4);
    
    private final int code;

    Role(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}

public class Admin extends User {
    private Role role;
    private int workingHours;

    // CONSTRUCTORS
    // Admin()
    // Admin(username: String, password: String)
    // Admin(username: String, password: String, role: Role, workingHours: int)

    @Override
    public String toString() {
        return "Role: " + role + "\nWorking Hours: " + workingHours;
    }

    public void create(Entity e, Database db){
        // DB OPERATION TO CREATE
    }

    public void get(int index, String tableName, Database db){
        // DB OPERATION this uses the get method in db
    }

    public void update(int index, Entity e, String tableName, Database db){
        // update 
    }

    public void delete(Entity e, Database db){
        // delete entity from DB
    }

}
