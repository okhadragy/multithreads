package Entity;

import java.util.ArrayList;

public class Order implements Entity {
    private String id;
    private String customerId;
    private ArrayList<String> products;
    private double total;
    private PaymentMethod paymentMethod;
    private Status status;

    public Order(String id, String customerId, ArrayList<String> products, double total, PaymentMethod paymentMethod, Status status) {
        setId(id);
        setCustomerId(customerId);
        setProducts(products);
        setTotal(total);
        setPaymentMethod(paymentMethod);
        setStatus(status);
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer Id cannot be null or empty.");
        }
        this.customerId = customerId;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        if (products == null) {
            throw new IllegalArgumentException("Products cannot be null.");
        }

        for (String product : products) {
            if (product == null || product.trim().isEmpty()) {
                throw new IllegalArgumentException("Product Id cannot be null or empty.");
            }
        }
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total<0) {
            throw new IllegalArgumentException("Total cannot be negative.");
        }
        this.total = total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment Method cannot be null.");
        }
        this.paymentMethod = paymentMethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
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
        if (obj instanceof Order) {
            return this.id == ((Order) obj).id;
        }
        return false;
    }
}