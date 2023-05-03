package cart.entity;

public class CartEntity {
    private final Long id;
    private final Long productId;
    private final Long memberId;

    public CartEntity(final Long id, final Long productId, final Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public CartEntity(final Long productId, final Long memberId) {
        this.id = null;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
