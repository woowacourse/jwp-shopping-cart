package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "사용자 아이디가 필요합니다.")
    private long memberId;

    @NotNull(message = "상품 아이디가 필요합니다.")
    private long productId;

    private int count;

    public CartRequest() {
    }

    public CartRequest(long memberId, long productId, int count) {
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
