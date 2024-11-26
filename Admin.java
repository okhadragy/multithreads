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

    // Default role is super-admin
    // Default working hours is 0

    public Admin(){
        super();
        this.role = Role.superadmin;
        this.workingHours = 0;
    }

    public Admin(String username, String password){
        super(username, password);
        this.role = Role.superadmin;
        this.workingHours = 0;
    }

    public Admin(String username, String password, Role role, int workingHours){
        super(username, password);
        this.role = role;
        this.workingHours = workingHours;
    }

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
