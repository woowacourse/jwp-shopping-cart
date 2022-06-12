package woowacourse.shoppingcart.application.dto;

public class AddCartServiceRequest {

    private final long memberId;
    private final long productId;

    public AddCartServiceRequest(long memberId, long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
