package cart.dao;

import cart.entity.Cart;

public interface CartDao {
    int insert(Cart cart);

    int delete(int cartId, String email);
}
