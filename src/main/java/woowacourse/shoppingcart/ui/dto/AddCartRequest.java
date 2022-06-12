package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.AddCartServiceRequest;

import javax.validation.constraints.NotNull;

public class AddCartRequest {

    @NotNull(message = "상품 정보는 빈 값일 수 없습니다.")
    private Long productId;

    public AddCartRequest() {
    }

    public AddCartRequest(Long productId) {
        this.productId = productId;
    }

    public AddCartServiceRequest toServiceRequest(long memberId) {
        return new AddCartServiceRequest(memberId, productId);
    }

    public Long getProductId() {
        return productId;
    }
}
