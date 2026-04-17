package Lab_11;

public class CartItem {
    // Attributes
    private Product product;
    private int quantity;

    // Constructor
    public CartItem (Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter methods
    public Product getProduct () { return this.product; }
    public int getQuantity () { return this.quantity; }

}
