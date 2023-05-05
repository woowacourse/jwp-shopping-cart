package cart.entity;

public class Cart {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    private Cart(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static Cart of(Long id, Long memberId, Long productId) {
        return new Cart(id, memberId, productId);
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
