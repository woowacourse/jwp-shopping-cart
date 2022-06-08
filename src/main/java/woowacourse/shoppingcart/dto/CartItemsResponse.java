package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemsResponse {

    private List<ProductResponse> carts;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<ProductResponse> carts) {
        this.carts = carts;
    }

    public List<ProductResponse> getCarts() {
        return carts;
    }
}
