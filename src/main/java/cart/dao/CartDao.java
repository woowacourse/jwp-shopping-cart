package cart.dao;

import cart.entity.Cart;

import java.util.List;

public interface CartDao {
    int insert(int productId, String email);

    int delete(int cartId, String email);

    List<Cart> selectById(String email);
}
