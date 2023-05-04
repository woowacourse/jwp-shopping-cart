package cart.entity;

public class PutCart {

    private final Long memberId;
    private final Long productId;

    public PutCart(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
