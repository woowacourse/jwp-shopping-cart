package cart.dao.cart;

import java.util.List;

import cart.dao.entity.Product;
import cart.dao.entity.User;

public interface CartDao {
    Long addProduct(User user, Long productId);

    List<Product> findAllProductInCart(User user);

    void removeProductInCart(User user, Long productId);
}
