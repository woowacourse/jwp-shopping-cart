package cart.entity;

public class CartEntity {
    private final int userId;
    private final int productId;

    public CartEntity(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}
