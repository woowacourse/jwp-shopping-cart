package cart.domain.cart;

import java.util.List;

public class Cart {

    private final Long cartId;
    private final Long memberId;
    private final CartItems cartItems;

    public Cart(Long cartId, Long memberId, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.cartItems = new CartItems(cartItems);
    }

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeItem(int cartItemId) {
        cartItems.remove(cartItemId);
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<CartItem> getCartItems() {
        return cartItems.getItems();
    }
}
