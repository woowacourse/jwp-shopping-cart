package cart.dao;

import cart.entity.CartEntity;

import java.util.List;

public interface CartDao {

    Long insert(Long userId, Long productId);

    List<CartEntity> findByUserId(Long userId);

    void deleteById(Long id);
}
