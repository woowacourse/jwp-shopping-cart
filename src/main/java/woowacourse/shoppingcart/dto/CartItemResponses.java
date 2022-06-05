package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemResponses {
    private List<CartItemResponse> cart;

    public CartItemResponses() {
    }

    public CartItemResponses(List<CartItemResponse> cart) {
        this.cart = cart;
    }

    public List<CartItemResponse> getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "CartItemResponses{" +
                "cart=" + cart +
                '}';
    }
}
