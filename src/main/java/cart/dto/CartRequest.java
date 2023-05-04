package cart.dto;

import java.util.List;

public class CartRequest {
    private final long memberId;
    private final List<Long> productId;

    public CartRequest(long memberId, List<Long> productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public long getMemberId() {
        return memberId;
    }

    public List<Long> getProductId() {
        return productId;
    }
}
