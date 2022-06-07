package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.CartUpdateServiceRequest;

public class CartUpdateRequest {

    private Long productId;
    private Integer quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(final Long productId, final Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartUpdateServiceRequest toServiceDto() {
        return new CartUpdateServiceRequest(productId, quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
