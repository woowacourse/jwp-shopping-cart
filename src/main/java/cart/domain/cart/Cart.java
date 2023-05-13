package cart.domain.cart;

public class Cart {

    private Long id;
    private final Long productId;
    private final Long memberId;
    private Quantity quantity;

    public Cart(final Long id, final Long productId, final Long memberId, final Quantity quantity) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
    }

    public Cart(final Long productId, final Long memberId, final Quantity quantity) {
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
    }

    public Cart(final Long productId, final Long memberId) {
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

    public Quantity getQuantity() {
        return quantity;
    }
}
