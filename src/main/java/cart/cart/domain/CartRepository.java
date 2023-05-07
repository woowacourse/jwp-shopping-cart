package cart.cart.domain;

import cart.product.domain.Product;
import cart.user.domain.CartUser;
import java.util.List;

public interface CartRepository {
    Long addProductInCart(CartUser cartUser, Product product);

    Cart findCartByCartUser(CartUser cartUser);

    List<Cart> findAll();

    void deleteProductInCart(CartUser cartUser, Product product);
}
