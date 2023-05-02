package cart.dto;

public class CartCreateRequest {

    private final Long productId;
    private final String memberEmail;

    public CartCreateRequest(final Long productId, final String memberEmail) {
        this.productId = productId;
        this.memberEmail = memberEmail;
    }

    public Long getProductId() {
        return productId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }
}
