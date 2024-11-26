public abstract class Entity {
    private Boolean saved;

    public Entity(){
        saved = false;
    }

    public boolean isSaved(){
        return saved;
    }

    public void save(Database db, Admin admin, String tableName){
        saved = true;
        (db.get(admin, tableName)).add(this);
    }

    abstract public void save(Database db, Admin admin);

    public void delete(Database db, Admin admin, String tableName){
        (db.get(admin, tableName)).delete(this);
    }

    abstract public void delete(Database db, Admin admin);
     
}
