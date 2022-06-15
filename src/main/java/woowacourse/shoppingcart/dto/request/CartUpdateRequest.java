package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class CartUpdateRequest {

    @NotNull(message = "제품id는 필수 항목입니다.")
    private Long productId;

    @NotNull(message = "수량은 필수 항목입니다.")
    private Integer quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
