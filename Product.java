package Lab_11;

class Product {
    // Attributes
    private int productId;
    private String name;
    private int price;
    private int stock;

    // Constructor 
    public Product (int id, String name, int price, int stock) {
        this.productId = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    // Getter methods 
    public int getProductId () { return this.productId; }
    public String getName () { return this.name; }
    public int getPrice () { return this.price; }
    public int getStock () { return this.stock; }

}
    
