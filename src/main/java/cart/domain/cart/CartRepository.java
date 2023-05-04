package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.CartUser;
import java.util.List;

public interface CartRepository {
    Long addProductInCart(Cart cart, Product product);

    Cart findCartByCartUser(CartUser cartUser);

    List<Cart> findAll();
}
