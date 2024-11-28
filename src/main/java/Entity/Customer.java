package Entity;

import java.util.ArrayList;

public class Customer extends User {
    private double balance;
    private String address;
    private Gender gender;
    private ArrayList<String> interests;
    private String cart;
    
    public Customer(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender, String orderId){
        super(username, password, Role.customer, dateOfBirth);
        this.balance = balance;
        this.password = password;
        this.gender = gender;
        interests = new ArrayList<>();
        cart = orderId;
    }
}
