package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class AddCartRequest {

    @NotNull(message = "상품 정보는 빈 값일 수 없습니다.")
    private Long productId;

    public AddCartRequest() {
    }

    public AddCartRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
