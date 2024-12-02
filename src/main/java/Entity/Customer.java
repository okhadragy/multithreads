package Entity;

import java.util.ArrayList;

public class Customer extends User {
    private double balance;
    private String address;
    private Gender gender;
    private ArrayList<String> interests;
    private String cart;
    
    public Customer(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender, String cart){
        super(username, password, Role.customer, dateOfBirth);
        setBalance(balance);
        setGender(gender);
        interests = new ArrayList<>();
        setCart(cart);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    @Override
    public void setRole(Role role) {
        super.setRole(Role.customer);
    }

    @Override
    public String toString() {
        return super.toString()+"\nBalance: "+balance+"\nAddress: "+address+"\nGender: "+gender;
    }
}
