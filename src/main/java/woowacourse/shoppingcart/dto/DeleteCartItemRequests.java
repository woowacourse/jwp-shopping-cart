package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.CartItems;

public class DeleteCartItemRequests {

    private List<DeleteCartItemRequest> cartItems;

    public DeleteCartItemRequests() {
    }

    public DeleteCartItemRequests(List<DeleteCartItemRequest> deleteCartItemRequests) {
        this.cartItems = deleteCartItemRequests;
    }

    public List<DeleteCartItemRequest> getCartItems() {
        return cartItems;
    }

    public CartItems toCartItems() {
        var cartItemIds = cartItems.stream()
                .map(it -> new CartItem(it.getId()))
                .collect(Collectors.toList());

        return new CartItems(cartItemIds);
    }

    public List<Long> getIds() {
        return cartItems.stream()
                .map(DeleteCartItemRequest::getId)
                .collect(Collectors.toList());
    }
}
