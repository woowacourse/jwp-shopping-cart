package cart.entity.cart;

public class Cart {

    private final Long id;
    private final Long memberId;
    private final Long productId;
    private final int count;

    public Cart(final Long memberId, final Long productId, final int count) {
        this.id = null;
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
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

    public int getCount() {
        return count;
    }
}
