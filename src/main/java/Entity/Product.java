package Entity;

public class Product implements Entity, Comparable<Product> {
    private String id;
    private String name;
    private String description;
    private String image;
    private double price;
    private Category category;
    private int quantity;


    public Product(String name, String desc, String image,double price, Category category){
        setId(id);
        setName(name);
        setDescription(desc);
        setPrice(price);
        setCategory(category);
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
        if (description == null ) {
           throw new IllegalArgumentException("Description cannot be null or empty.");
       }
       this.description = description;
   }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (category == null) {
          throw new IllegalArgumentException("Category cannot be null.");
        }
      this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Object getKey(){
        return this.id;
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
}
