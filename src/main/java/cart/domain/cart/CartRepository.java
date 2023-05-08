package cart.domain.cart;

import cart.domain.product.Product;

public interface CartRepository {

    void create(long userId);

    Cart findByUserId(long userId);

    long saveProductToCart(Cart cartToAdd, Product product);

    void deleteProductFromCart(Cart cartToDelete, Product product);
}
