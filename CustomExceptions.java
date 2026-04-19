package Lab_11;

class ProductNotFoundException extends Exception {
    private final int productId;

    public ProductNotFoundException(int productId) {
        super("Product with id " + productId + " was not found in the cart.");
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }
}

class InsufficientStockException extends Exception {
    private final String productName;
    private final int requested;
    private final int available;

    public InsufficientStockException(String productName, int requested, int available) {
        super("Insufficient stock for " + productName + ". Requested: " + requested + ", Available: " + available);
        this.productName = productName;
        this.requested = requested;
        this.available = available;
    }

    public String getProductName() {
        return productName;
    }

    public int getRequested() {
        return requested;
    }

    public int getAvailable() {
        return available;
    }
}

class InvalidQuantityException extends Exception {
    private final int quantity;

    public InvalidQuantityException(int quantity) {
        super("Invalid quantity: " + quantity + ". Quantity must be greater than 0.");
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}

class EmptyCartException extends Exception {

    public EmptyCartException() {
        super("Checkout Failed! Empty cart!");
    }

}

class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
