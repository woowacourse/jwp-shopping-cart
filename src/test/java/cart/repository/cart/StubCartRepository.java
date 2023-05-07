package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.cart.CartProduct;
import cart.domain.cart.CartProductId;
import cart.domain.user.UserId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StubCartRepository implements CartRepository {

    private final Map<UserId, Cart> userIdToCart = new HashMap<>();
    private long maxId = 1;
    private long maxCartProductId = 1;

    @Override
    public Cart save(final Cart cart) {
        final UserId userId = cart.getUserId();
        final CartId cartId = new CartId(maxId);
        final var CartProducts = cart.getCartProducts().getCartProducts();
        final ArrayList<CartProduct> cartProducts = new ArrayList<>();
        for (final CartProduct cartProduct : CartProducts) {
            cartProducts.add(
                    new CartProduct(new CartProductId(maxCartProductId), cartProduct.getProductId()));
            maxCartProductId++;
        }
        maxId++;
        final Cart savedCart = new Cart(cartId, userId, cartProducts);
        userIdToCart.put(userId, savedCart);
        return savedCart;
    }

    @Override
    public Optional<Cart> findByUserId(final UserId userId) {
        if (userIdToCart.containsKey(userId)) {
            return Optional.of(userIdToCart.get(userId));
        }
        return Optional.empty();
    }
}
