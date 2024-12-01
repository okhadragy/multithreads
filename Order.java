package Entity;

public class Order implements Entity{
    String id;
   public Order(String id){
       setId(id);
   }

    @Override
    public String getKey() {
        return this.id;
    }

    public void setId(String id) {
         if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        this.id = id;
    }
    
}

   
