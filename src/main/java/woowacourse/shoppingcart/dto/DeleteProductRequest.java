package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteProductRequest {

    private List<IdRequest> cartItems;

    private DeleteProductRequest() {
    }

    public DeleteProductRequest(List<IdRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<IdRequest> getCartItems() {
        return cartItems;
    }
}
