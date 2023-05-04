package cart.dao;

import cart.entity.CartEntity;

import java.util.List;

public interface CartDao {
    int insert(CartEntity cart);

    List<CartEntity> selectByUserId(int userId);

    int updateQuantity(CartEntity cart);

    int delete(int userId, int productId);
}
