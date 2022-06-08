package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCartItemRequest {

    private List<DeleteCartItemElement> cartItems;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(List<DeleteCartItemElement> cartItems) {
        this.cartItems = cartItems;
    }

    public List<DeleteCartItemElement> getCartItems() {
        return cartItems;
    }

    public List<Long> generateIds() {
        return cartItems.stream()
                .map(DeleteCartItemElement::getId)
                .collect(Collectors.toList());
    }
}
