package woowacourse.shoppingcart.dto.cart;

import javax.validation.constraints.NotNull;

public class CartItemUpdateRequest {

    @NotNull
    private Integer count;

    public CartItemUpdateRequest(Integer count) {
        this.count = count;
    }

    private CartItemUpdateRequest() {
    }

    public Integer getCount() {
        return count;
    }
}
