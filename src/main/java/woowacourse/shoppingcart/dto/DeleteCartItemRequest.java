package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartItemRequest {

    private List<IdRequest> cartItems;

    private DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(List<IdRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<IdRequest> getCartItems() {
        return cartItems;
    }
}
