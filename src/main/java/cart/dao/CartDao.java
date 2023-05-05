package cart.dao;

import cart.entity.CartEntity;

import java.util.List;

public interface CartDao {
    List<CartEntity> findByUserId(Long userId);
}
