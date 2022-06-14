package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

public class AddCartItemRequest {

    @Positive(message = "상품번호는 양의 정수만 허용합니다.")
    private long productId;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
