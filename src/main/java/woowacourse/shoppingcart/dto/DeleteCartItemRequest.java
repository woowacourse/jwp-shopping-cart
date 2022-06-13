package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCartItemRequest {

    private List<IdRequest> cartItems;

    private DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(List<IdRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Long> getCartItems() {
        return cartItems.stream()
                .map(IdRequest::getId)
                .collect(Collectors.toList());
    }
}
