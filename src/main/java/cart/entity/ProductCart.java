package cart.entity;

public class ProductCart {

    private final Long id;
    private final Long productId;
    private final Long memberId;

    public ProductCart(Long productId, Long memberId) {
        this(null, productId, memberId);
    }

    public ProductCart(Long id, Long productId, Long memberId) {
        this.id = id;
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
}
