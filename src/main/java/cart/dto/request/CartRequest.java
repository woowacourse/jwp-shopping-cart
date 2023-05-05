package cart.dto.request;

import javax.validation.constraints.Positive;

public class CartRequest {

    @Positive(message = "상품 아이디는 1 이상입니다.")
    private Long productId;

    private int count = 1;

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
