package cart.dao.entity;

import java.time.LocalDateTime;

public class Cart {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final LocalDateTime createdAt;

    public Cart(final Long userId, final Long productId) {
        this(null, userId, productId, null);
    }

    public Cart(final Long id, final Long userId, final Long productId, final LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
