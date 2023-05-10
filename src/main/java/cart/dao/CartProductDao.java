package cart.dao;

import cart.entity.Cart;

import java.util.List;

public interface CartProductDao {
    List<Cart> selectById(String email);
}
