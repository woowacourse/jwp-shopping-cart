package cart.entity;

public class CartEntity {

    private final Integer id;
    private final Integer userId;
    private final Integer productId;

    public CartEntity(final Integer id, final Integer userId, final Integer productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public CartEntity(final Integer userId, final Integer productId) {
        this(null, userId, productId);
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }
}
