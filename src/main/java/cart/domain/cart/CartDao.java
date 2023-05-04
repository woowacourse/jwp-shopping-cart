package cart.domain.cart;

import cart.domain.Product;

import java.util.List;

public interface CartDao {
    Long addProduct(Cart cart);

    List<Product> findProductsByUserId(Long userId);
}
