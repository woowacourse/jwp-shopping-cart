package cart.entity;

public final class CartItemEntity {

    private final long id;
    private final long userId;
    private final long productId;
    private final int count;

    public CartItemEntity(long id, long userId, long productId, int count) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
