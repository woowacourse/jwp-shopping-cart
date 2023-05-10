package cart.dto.view;

import cart.dto.CartProductResponse;

import java.util.List;

public class CartProductResponseWrapper {
    private List<CartProductResponse> carts;

    public CartProductResponseWrapper() {
    }

    private CartProductResponseWrapper(List<CartProductResponse> carts) {
        this.carts = carts;
    }

    public static CartProductResponseWrapper from(List<CartProductResponse> carts) {
        return new CartProductResponseWrapper(carts);
    }

    public List<CartProductResponse> getCarts() {
        return carts;
    }
}
