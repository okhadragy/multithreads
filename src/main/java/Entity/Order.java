package Entity;

public class Order implements Entity{
    String id;

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
