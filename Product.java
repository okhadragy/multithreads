class AlreadyExists extends RuntimeException {
    AlreadyExists(String s){
        super(s);
    }
}

class AlreadySaved extends RuntimeException {
    AlreadySaved(String s){
        super(s);
    }
}

public class Product extends Entity implements Comparable<Product> {
    private static int idCounter = 1;

    private int id;
    private String name;
    private String description;
    private double price;
    private Category category;
    private int quantity;

    // Default quantity is 1

    Product(String name, String desc, double price, Category category){
        this.id = idCounter++;
        this.name = name;
        this.description = desc;
        this.price = price;
        this.category = category;
        this.quantity = 1;
    }

    Product(String name, String desc, double price, Category category, int quantity){
        this.id = idCounter++;
        this.name = name;
        this.description = desc;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }


    public int getId(){
        return id;
    }

    public void setId(int id, Database db, Admin admin){
        Table<Product> productList = db.get(admin, "products");

        for (int i = 0; i < productList.getnumberOfElements(); i++) {
            if(id == (productList.get(i)).id) {
                throw new AlreadyExists("Product ID already exists");
            }    
        }

        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nDescription: " + description + "\nPrice: " + price + "\nCategory: " + category + "\nQuantity: " + quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product){
            return this.id == ((Product)obj).id;
        }
        return false;
    }

    public void save(Database db, Admin admin){
        if(this.isSaved()){
            throw new AlreadySaved("Product already saved");
        }

        else{
            super.save(db, admin, "products");
        }
    }

    public void delete(Database db, Admin admin){
        super.delete(db, admin, "products");
    }

    @Override
    public int compareTo(Product p) {
        if(price == p.price) return 0;
        else if(price > p.price) return 1;
        return -1;
    }
    
}
