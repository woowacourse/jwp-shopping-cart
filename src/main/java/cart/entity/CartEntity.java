package cart.entity;

public class CartEntity {

    private Long id;
    private Long memberId;
    private Long productId;

    public CartEntity(final Long memberId, final Long productId) {
        this(null, memberId, productId);
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
