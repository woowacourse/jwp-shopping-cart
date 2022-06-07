package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

public class AddCartItemRequest {

    @Positive(message = "상품번호는 양의 정수만 허용합니다.")
    private long product_id;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(long product_id) {
        this.product_id = product_id;
    }

    public long getProduct_id() {
        return product_id;
    }
}
