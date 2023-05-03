package cart.entity.cart;

public class Cart {

    private final Long id;
    private final Long memberId;
    private final Long productId;
    private final int count;

    public Cart(final Long id, final Long memberId, final Long productId, final int count) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
    }
}
