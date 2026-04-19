package Lab_11;

public class CartItem {
    /* Each cart item has two attributes, a product and its quantity selected 
    by the user. He should not be able to alter the product once chosen meaning that 
    he should drop the product altogether if he does not want to buy it. 
    */
    // Allowing the user to alter the product can lead to inaccurate subtotal and consequently the 
    // complete total bill.

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Each specific cartItem has a subtotal depending on price and quantity selected.
    public int getSubtotal() {
        return product.getPrice() * quantity;
    }

}
