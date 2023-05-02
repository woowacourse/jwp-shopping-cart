package cart.domain;

import java.util.List;

public class Cart {

    private final Long cartId;
    private final Long memberId;
    private final List<CartItem> cartItems;

    public Cart(Long cartId, Long memberId, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.cartItems = cartItems;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }
}
