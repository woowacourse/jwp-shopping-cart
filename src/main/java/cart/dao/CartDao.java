package cart.dao;

import cart.entity.Cart;
import cart.entity.Product;
import cart.entity.vo.Email;

import java.util.Map;

public interface CartDao {

    long create(final Cart cart);

    Cart findById(final long id);

    Map<Cart, Product> findProductsByUserEmail(final Email userEmail);

    void delete(final long id);
}
