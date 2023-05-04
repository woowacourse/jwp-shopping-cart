package cart.domain.cart;

public class Cart {

    private final Long productId;
    private final Long memberId;
    private final Quantity quantity;

    public Cart(final Long productId, final Long memberId, final Quantity quantity) {
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
