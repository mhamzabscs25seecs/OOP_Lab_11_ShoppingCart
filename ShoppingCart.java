package Lab_11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {
    private final ArrayList<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cartItems);
    }

    public void addProduct(Product product, int quantity) throws InvalidQuantityException, InsufficientStockException {
        validateQuantity(quantity);
        CartItem existingItem = findByProductId(product.getProductId());
        int newQuantity = quantity + (existingItem == null ? 0 : existingItem.getQuantity());

        if (newQuantity > product.getStock()) {
            throw new InsufficientStockException(product.getName(), newQuantity, product.getStock());
        }

        if (existingItem == null) {
            cartItems.add(new CartItem(product, quantity));
        } else {
            existingItem.setQuantity(newQuantity);
        }
    }

    public void removeProduct(int productId) throws ProductNotFoundException {
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getProductId() == productId) {
                iterator.remove();
                return;
            }
        }
        throw new ProductNotFoundException(productId);
    }

    public void updateQuantity(int productId, int quantity)
            throws ProductNotFoundException, InvalidQuantityException, InsufficientStockException {
        validateQuantity(quantity);
        CartItem item = findByProductId(productId);
        if (item == null) {
            throw new ProductNotFoundException(productId);
        }
        if (quantity > item.getProduct().getStock()) {
            throw new InsufficientStockException(item.getProduct().getName(), quantity, item.getProduct().getStock());
        }
        item.setQuantity(quantity);
    }

    public int calculateTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    public String checkout(String paymentGateway) throws EmptyCartException, InvalidPaymentException, InsufficientStockException {
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }

        int total = calculateTotal();
        if (total > 5000 && isCashOnDelivery(paymentGateway)) {
            throw new InvalidPaymentException("Orders above PKR 5000 require advance payment. COD is not allowed.");
        }

        for (CartItem item : cartItems) {
            item.getProduct().decreaseStock(item.getQuantity());
        }

        cartItems.clear();
        return "Order placed successfully via " + paymentGateway + ". Total: PKR " + total;
    }

    private CartItem findByProductId(int productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                return item;
            }
        }
        return null;
    }

    private void validateQuantity(int quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
    }

    private boolean isCashOnDelivery(String paymentGateway) {
        if (paymentGateway == null) {
            return false;
        }
        String normalized = paymentGateway.trim().toLowerCase();
        return normalized.equals("cash-on-delivery") || normalized.equals("cod");
    }
}
