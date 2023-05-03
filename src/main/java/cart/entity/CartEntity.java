package cart.entity;

public class CartEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartEntity(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
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
