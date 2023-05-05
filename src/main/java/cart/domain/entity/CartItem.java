package cart.domain.entity;

public class CartItem {

    private final Long id;
    private final long memberId;
    private final long productId;

    private CartItem(final Long id, final long memberId, final long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartItem of(final long memberId, final long productId) {
        return new CartItem(null, memberId, productId);
    }

    public static CartItem of(final Long id, final long memberId, final long productId) {
        return new CartItem(id, memberId, productId);
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
