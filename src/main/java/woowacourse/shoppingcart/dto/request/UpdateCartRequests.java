package woowacourse.shoppingcart.dto.request;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;

public class UpdateCartRequests {

    private List<UpdateCartRequest> cartItems;

    private UpdateCartRequests() {
    }

    public UpdateCartRequests(List<UpdateCartRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<UpdateCartRequest> getCartItems() {
        return cartItems;
    }

    public List<CartItem> toCart() {
        return cartItems.stream()
                .map(UpdateCartRequest::toCart)
                .collect(Collectors.toList());
    }

    public List<Long> toCartIds() {
        return cartItems.stream()
                .map(UpdateCartRequest::getId)
                .collect(Collectors.toList());
    }
}
