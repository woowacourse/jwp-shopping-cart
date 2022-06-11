package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.CartItems;

public class UpdateCartItemRequests {
    private List<UpdateCartItemRequest> cartItems;

    public UpdateCartItemRequests() {
    }

    public UpdateCartItemRequests(List<UpdateCartItemRequest> updateCartItemRequest) {
        this.cartItems = updateCartItemRequest;
    }

    public List<UpdateCartItemRequest> getCartItems() {
        return cartItems;
    }

    public CartItems toCartItems() {
        var cartItemIds = cartItems.stream()
                .map(it -> new CartItem(it.getId()))
                .collect(Collectors.toList());

        return new CartItems(cartItemIds);
    }
}
