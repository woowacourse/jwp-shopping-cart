package woowacourse.shoppingcart.dto;

import java.util.List;

public class NewOrderRequest {
    private List<Long> cartItemIds;

    public NewOrderRequest() {
    }

    public NewOrderRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
