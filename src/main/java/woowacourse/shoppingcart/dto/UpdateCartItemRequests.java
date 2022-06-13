package woowacourse.shoppingcart.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

public class UpdateCartItemRequests {

    @NotNull
    private List<UpdateCartItemRequest> cartItems;

    private UpdateCartItemRequests() {
    }

    public UpdateCartItemRequests(List<UpdateCartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<UpdateCartItemRequest> getCartItems() {
        return cartItems;
    }
}
