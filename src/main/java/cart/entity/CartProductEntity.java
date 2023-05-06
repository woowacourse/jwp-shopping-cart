package cart.entity;

public class CartProductEntity {
    private final Long id;
    private final Long productId;
    private final Long memberId;

    public CartProductEntity(final Long id, final Long productId, final Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public CartProductEntity(final Long productId, final Long memberId) {
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
