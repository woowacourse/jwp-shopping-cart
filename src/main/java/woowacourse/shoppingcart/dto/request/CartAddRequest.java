package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class CartAddRequest {

    @NotNull(message = "제품id는 필수 항목입니다.")
    private Long productId;

    public CartAddRequest() {
    }

    public CartAddRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
