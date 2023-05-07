package cart.dto.request;

import javax.validation.constraints.Positive;

public class CartRequest {

    @Positive(message = "상품 아이디는 1 이상입니다.")
    private final Long productId;

    private final int count;

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
