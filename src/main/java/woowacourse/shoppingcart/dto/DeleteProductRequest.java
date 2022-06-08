package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Id;

import java.util.List;

public class DeleteProductRequest {

    private List<Id> cartItems;

    private DeleteProductRequest() {
    }

    public DeleteProductRequest(List<Id> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Id> getCartItems() {
        return cartItems;
    }
}
