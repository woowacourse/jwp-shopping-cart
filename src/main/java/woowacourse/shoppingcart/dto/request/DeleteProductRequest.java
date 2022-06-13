package woowacourse.shoppingcart.dto.request;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteProductRequest {

    private List<CartIdRequest> cartItems;

    private DeleteProductRequest() {
    }

    public DeleteProductRequest(List<CartIdRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartIdRequest> getCartItems() {
        return cartItems;
    }

    public List<Long> toCartIds() {
        return cartItems.stream()
                .map(CartIdRequest::getId)
                .collect(Collectors.toList());
    }
}
