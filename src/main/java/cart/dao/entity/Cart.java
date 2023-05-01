package cart.dao.entity;

import java.time.LocalDateTime;

public class Cart {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final int count;
    private final LocalDateTime createdAt;

    public Cart(final Long userId, final Long productId, final int count) {
        this(null, userId, productId, count, null);
    }

    public Cart(final Long id, final Long userId, final Long productId, final int count, final LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", count=" + count +
                '}';
    }
}
