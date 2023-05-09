package cart.controller.dto;

public class CartRequest {

    private final Long memberId;
    private final Long productId;

    public CartRequest(Long memberId, Long productId) {
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
