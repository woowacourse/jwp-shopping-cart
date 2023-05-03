package cart.entity;

public final class CartItemEntity {
    private final Long id;
    private final Long userId;
    private final Long productId;
    private final Integer count;

    public CartItemEntity(Long id, Long userId, Long productId, Integer count) {
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

    public Integer getCount() {
        return count;
    }
}
