package cart.entiy.cart;

import cart.domain.cart.Cart;
import cart.entiy.user.UserEntityId;

public class CartEntity {

    private final CartEntityId cartId;
    private final UserEntityId userEntity;

    public CartEntity(final CartEntityId cartId, final UserEntityId userEntity) {
        this.cartId = cartId;
        this.userEntity = userEntity;
    }

    public CartEntity(final Long cartEntityId, final Long userEntityId) {
        this(new CartEntityId(cartEntityId), new UserEntityId(userEntityId));
    }

    public CartEntity(final Long cartEntityId, final CartEntity other) {
        this(new CartEntityId(cartEntityId), other.userEntity);
    }

    public static CartEntity from(final Cart cart) {
        return new CartEntity(
                CartEntityId.from(cart.getCartId()),
                UserEntityId.from(cart.getUser().getUserId())
        );
    }

    public CartEntityId getCartId() {
        return cartId;
    }

    public UserEntityId getUserEntityId() {
        return userEntity;
    }
}
