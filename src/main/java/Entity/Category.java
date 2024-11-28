package Entity;

import java.util.ArrayList;

public class Category implements Entity {
    private static int idCounter = 1;
    private String id;
    private String name;
    private String description;
    private ArrayList<String> products;

    public Category(String id, String name, String description, ArrayList<String> products){
        this.id = String.valueOf(idCounter);
        idCounter++;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nDescription: " + description + "\nProducts: " + products.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Category){
            return this.id == ((Category)obj).id;
        }
        return false;
    }
}