package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class UpdateQuantityRequest {

    @NotNull(message = "상품 수량은 빈 값일 수 없습니다.")
    private Integer quantity;

    public UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
