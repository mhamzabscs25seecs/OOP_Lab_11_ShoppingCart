package Lab_11;

class CustomExceptions {
    
}

class ProductNotFoundException extends Exception {
    private int productId;

    public ProductNotFoundException (int id) {
        super("Product not found");
        this.productId = id;
    }

    // Getter
    public int getProductId () { return this.productId;}

}

class InsufficientStockException extends Exception {
    private int stock;

    public InsufficientStockException (int stock) {
        super("Insufficient Stock");
    }   
    
}


public class test {

    public static void main (String [] args) {
        try {

    if (true) {
        throw new InsufficientStockException (10);
    }

}

catch (InsufficientStockException e) {
    System.out.println(e.getMessage());
}
    }
}
