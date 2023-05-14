package cart.persistence;

public class CartEntity {
    private Long id;
    private final Long productId;
    private final Long memberId;

    public CartEntity(Long id, Long productId, Long memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public CartEntity(Long productId, Long memberId) {
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
