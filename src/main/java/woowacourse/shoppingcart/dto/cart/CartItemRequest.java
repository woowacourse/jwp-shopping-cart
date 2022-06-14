package woowacourse.shoppingcart.dto.cart;

import javax.validation.constraints.NotNull;

public class CartItemRequest {

    @NotNull
    private Long productId;
    @NotNull
    private Integer count;

    public CartItemRequest(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    private CartItemRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }
}
