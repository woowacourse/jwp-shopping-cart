package cart.domain;

import java.time.LocalDateTime;

public class Cart {

    private final Long id;
    private final Long memberId;
    private final Long productId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Cart(final Long memberId, final Long productId) {
        this(null, memberId, productId, null, null);
    }

    public Cart(
            final Long id,
            final Long memberId,
            final Long productId,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
