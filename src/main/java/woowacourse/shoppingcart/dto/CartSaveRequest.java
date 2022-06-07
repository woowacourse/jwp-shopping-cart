package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.CartSaveServiceRequest;

public class CartSaveRequest {

    private Long productId;
    private Integer quantity;

    private CartSaveRequest() {
    }

    public CartSaveRequest(final Long productId, final Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartSaveServiceRequest toServiceDto() {
        return new CartSaveServiceRequest(productId, quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
