package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "상품 아이디가 필요합니다.")
    private long productId;

    private int count;

    public CartRequest() {
    }

    public CartRequest(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
