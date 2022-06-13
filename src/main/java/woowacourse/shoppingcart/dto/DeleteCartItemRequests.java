package woowacourse.shoppingcart.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DeleteCartItemRequests {

    @NotNull
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
