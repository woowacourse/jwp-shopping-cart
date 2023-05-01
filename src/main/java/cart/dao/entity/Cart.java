package cart.dao.entity;

public class Cart {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final int count;

    public Cart(final Long userId, final Long productId, final int count) {
        this(null, userId, productId, count);
    }

    public Cart(final Long id, final Long userId, final Long productId, final int count) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
