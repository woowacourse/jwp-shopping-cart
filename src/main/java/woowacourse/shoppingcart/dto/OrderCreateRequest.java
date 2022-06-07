package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderCreateRequest {
    private List<Long> cartItemIds;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
