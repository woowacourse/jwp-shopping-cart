package cart.persistence.entity;

public class CartEntity {
    private Long id;
    private final Long memberId;
    private final Long productId;

    public CartEntity(final Long memberId, final Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public CartEntity(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
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
}
