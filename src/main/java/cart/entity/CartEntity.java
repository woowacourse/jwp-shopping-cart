package cart.entity;

public class CartEntity {
    private final int userId;
    private final int productId;
    private final int quantity;

    public CartEntity(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
