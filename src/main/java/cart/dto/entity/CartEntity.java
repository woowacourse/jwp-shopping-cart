package cart.dto.entity;

public class CartEntity {
    private Long productId;
    private Long memberId;

    public CartEntity(Long productId, Long memberId) {
        this.productId = productId;
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
