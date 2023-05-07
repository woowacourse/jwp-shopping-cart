package cart.dto;

import java.util.List;

public class CartResponses {

    private final List<CartResponse> cartResponses;

    public CartResponses(final List<CartResponse> cartResponses) {
        this.cartResponses = cartResponses;
    }

    public List<CartResponse> getCartResponses() {
        return cartResponses;
    }
}
