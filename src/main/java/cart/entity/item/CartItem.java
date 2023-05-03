package cart.entity.item;

public class CartItem {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartItem(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public CartItem(final long memberId, final long productId) {
        this(null, memberId, productId);
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

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", productId=" + productId +
                '}';
    }
}
