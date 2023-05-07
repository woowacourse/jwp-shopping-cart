package cart.domain.cart;

import cart.domain.product.ProductId;
import cart.domain.user.UserId;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final CartId cartId;
    private final UserId userId;
    private final CartProducts cartProducts;

    public Cart(final CartId cartId, final UserId userId, final List<CartProduct> cartProducts) {
        this(cartId, userId, new CartProducts(cartProducts));
    }

    public Cart(final UserId userId) {
        this(new CartId(), userId, new CartProducts());
    }

    public Cart(final CartId cartId, final UserId userId, final CartProducts cartProducts) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartProducts = cartProducts;
    }

    public void addProduct(final ProductId productId) {
        cartProducts.add(productId);
    }

    public void deleteProduct(final Long cartProductId) {
        cartProducts.delete(cartProductId);
    }

    public CartId getCartId() {
        return cartId;
    }

    public UserId getUserId() {
        return userId;
    }

    public CartProducts getCartProducts() {
        return cartProducts;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cart cart = (Cart) o;
        return cartId.equals(cart.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}
