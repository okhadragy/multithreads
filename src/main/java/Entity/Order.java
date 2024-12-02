package Entity;

public class Order implements Entity{
    private String id;

    public Order(String id){
       setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
         if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        this.id = id;
    }

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Order){
            return this.id == ((Order)obj).id;
        }
        return false;
    }
}