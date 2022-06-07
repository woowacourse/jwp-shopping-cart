package woowacourse.shoppingcart.domain;

import java.util.List;

public class CartItems {

    private final long customerId;
    private final List<CartItem> cartItems;

    public CartItems(long customerId, List<CartItem> cartItems) {
        this.customerId = customerId;
        this.cartItems = cartItems;
    }

    public void add(CartItem newCartItem) {
        for (CartItem cartItem : cartItems) {
            cartItem.checkSameProduct(newCartItem);
        }
        cartItems.add(newCartItem);
    }

    public int size() {
        return cartItems.size();
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
