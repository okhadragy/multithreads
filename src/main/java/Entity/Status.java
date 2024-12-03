package Entity;

public enum Status {
    draft(1),
    processing(2),
    cancelled(3),
    closed(4),
    delivered(5),
    returned(6);
    
    private final int code;

    Status(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}