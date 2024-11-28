package Entity;

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

public class Product implements Entity, Comparable<Product> {
    private static int idCounter = 1;
    private String id;
    private String name;
    private String description;
    private String image;
    private double price;
    private Category category;
    private int quantity;

    // Default quantity is 1

    public Product(String name, String desc, String image,double price, Category category){
        this.id = String.valueOf(idCounter);
        idCounter++;
        this.name = name;
        this.description = desc;
        this.price = price;
        this.category = category;
        this.quantity = 1;
    }

    public Product(String name, String desc, String image, double price, Category category, int quantity){
        this(name, desc,image, price, category);
        this.quantity = quantity;
    }


    public String getId(){
        return id;
    }

    public void setId(String id){
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public int compareTo(Product p) {
        if(price == p.price) return 0;
        else if(price > p.price) return 1;
        return -1;
    }

    @Override
    public Object getKey(){
        return this.id;
    }
}
