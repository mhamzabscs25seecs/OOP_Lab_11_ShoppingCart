package Lab_11;

public class Product {
    private final int productId;
    private final String name;
    private final int price;
    private int stock;

    public Product(int productId, String name, int price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

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

    public void decreaseStock(int quantity) throws InsufficientStockException {
        if (quantity > stock) {
            throw new InsufficientStockException(name, quantity, stock);
        }
        stock -= quantity;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return String.format("%d - %s (PKR %d, Stock %d)", productId, name, price, stock);
    }
}
    
