package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartItemRequests {

    private List<DeleteCartItemRequest> cartItems;

    private DeleteCartItemRequests() {
    }

    public DeleteCartItemRequests(List<DeleteCartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<DeleteCartItemRequest> getCartItems() {
        return cartItems;
    }
}
