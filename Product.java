package Lab_11;

public class Product {
    // Attributes 
    /* we made id, name, price as final because if once we have created a product 
    only its stock (available quantity) should be changeable
    Suppose if price change occurs then we should create an entirely different 
    Product instance */
    /*  This strictness is enforced because if change is allowed then during checkout 
        it may happen that price change may be done causing system to throw errors.
        Also confusing the User using the system.    */

    private final int productId;       
    private final String name;
    private final int price;
    private int stock;


    // Constructor
    public Product(int productId, String name, int price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    /* Stock belongs to a specific product i.e stock alone has no existence
     e.g 5 stock has no meaning but product 'Headphones' having stock 5 has meaning.
     And also we need to be able to increase/ decrease stock so we define the following 
     methods */
    public void decreaseStock(int quantity) throws InsufficientStockException {
        if (quantity > stock) {
            throw new InsufficientStockException(this.name, quantity, this.stock);
        }
        stock -= quantity;
    }

    // We add this function leaving it as a future possibilty if we wish to extend our application 
    // and allow the customers to edit or cancel their orders.
    // or even if there is a case of refund request.
    public void increaseStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return String.format("%d - %s (PKR %,d, Stock %d)", productId, name, price, stock);
    }
}
    
