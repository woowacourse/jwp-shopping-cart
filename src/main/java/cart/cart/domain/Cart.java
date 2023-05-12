package cart.cart.domain;

public class Cart {
    private final long id;
    private final long userId;
    private final long productId;

    public Cart(final long id, final long userId, final long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public static Cart of(final long id, final long userId, final long productId) {
        return new Cart(id, userId, productId);
    }

    public long getId() {
        return this.id;
    }

    public long getUserId() {
        return this.userId;
    }

    public long getProductId() {
        return this.productId;
    }
}
