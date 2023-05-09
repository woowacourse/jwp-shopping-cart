package cart.entiy.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.user.UserId;

public class CartEntity {

    private final CartId cartId;
    private final UserId userId;

    public CartEntity(final CartId cartId, final UserId userId) {
        this.cartId = cartId;
        this.userId = userId;
    }

    public CartEntity(final Long cartId, final Long userId) {
        this(new CartId(cartId), new UserId(userId));
    }

    public CartEntity(final Long cartId, final CartEntity other) {
        this(new CartId(cartId), other.userId);
    }

    public static CartEntity from(final Cart cart) {
        return new CartEntity(cart.getCartId(), cart.getUserId());
    }

    public CartId getCartId() {
        return cartId;
    }

    public UserId getUserId() {
        return userId;
    }
}
