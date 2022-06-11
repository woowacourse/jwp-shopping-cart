package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

public class CartItems {

    private final List<CartItem> cartItems;

    public CartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Long> getProductIds() {
        return cartItems.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void checkValidIds(List<Long> cartItemIds) {
        var originCartItemIds = cartItems.stream()
                .map(CartItem::getCartItemId)
                .collect(Collectors.toList());

        if (!originCartItemIds.containsAll(cartItemIds)) {
            throw new NotInCustomerCartItemException();
        }
    }
}
