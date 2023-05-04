package cart.entity;

public class CartItemEntity {

    private final Long id;
    private final long memberId;
    private final long productId;

    private CartItemEntity(final Long id, final long memberId, final long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItemEntity of(final long memberId, final long productId) {
        return new CartItemEntity(null, memberId, productId);
    }

    public static CartItemEntity of(final Long id, final long memberId, final long productId) {
        return new CartItemEntity(id, memberId, productId);
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
