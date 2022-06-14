package woowacourse.shoppingcart.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemsResponse {
    @JsonProperty("cartItems")
    private List<CartItemResponse> cartItemResponseList;

    private CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItemResponse> cartItemResponseList) {
        this.cartItemResponseList = cartItemResponseList;
    }

    public List<CartItemResponse> getCartItemResponseList() {
        return cartItemResponseList;
    }
}
