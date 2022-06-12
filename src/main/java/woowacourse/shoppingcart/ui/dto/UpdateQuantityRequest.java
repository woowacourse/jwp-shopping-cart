package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.UpdateQuantityServiceRequest;

import javax.validation.constraints.NotNull;

public class UpdateQuantityRequest {

    @NotNull(message = "상품 수량은 빈 값일 수 없습니다.")
    private Integer quantity;

    public UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public UpdateQuantityServiceRequest toServiceRequest(long memberId, long cartId) {
        return new UpdateQuantityServiceRequest(memberId, cartId, quantity);
    }

    public Integer getQuantity() {
        return quantity;
    }
}
