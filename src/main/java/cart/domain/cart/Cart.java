package cart.domain.cart;

public class Cart {
    private Long id;
    private final Long productId;
    private final Long memberId;

    public Cart(Long id, Long productId, Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Cart(Long productId, Long memberId) {
        this(null, productId, memberId);
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
