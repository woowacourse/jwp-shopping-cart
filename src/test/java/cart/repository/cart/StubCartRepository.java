package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.cart.CartProduct;
import cart.domain.cart.CartProductId;
import cart.domain.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StubCartRepository implements CartRepository {

    private final Map<User, Cart> carts = new HashMap<>();
    private long maxId = 1;
    private long maxCartProductId = 1;

    @Override
    public Cart save(final Cart cart) {
        final User user = cart.getUser();
        final CartId cartId = new CartId(maxId);
        final var CartProducts = cart.getCartProducts().getCartProducts();
        final ArrayList<CartProduct> cartProducts = new ArrayList<>();
        for (final CartProduct cartProduct : CartProducts) {
            cartProducts.add(new CartProduct(new CartProductId(maxCartProductId), cartProduct.getProduct()));
            maxCartProductId++;
        }
        maxId++;
        final Cart savedCart = new Cart(cartId, user, cartProducts);
        carts.put(user, savedCart);
        return savedCart;
    }

    @Override
    public Optional<Cart> findByUser(final User user) {
        if (carts.containsKey(user)) {
            return Optional.of(carts.get(user));
        }
        return Optional.empty();
    }
}
