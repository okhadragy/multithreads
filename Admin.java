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
        e.save(db, this);
    }

    public Entity get(int index, String tableName, Database db){
        return (db.get(this, tableName)).get(index);
    }

    public void update(int index, Entity e, String tableName, Database db){
        (db.get(this, tableName)).update(index, e);
    }

    public void delete(Entity e, Database db){
        e.delete(db, this);
    }

}
