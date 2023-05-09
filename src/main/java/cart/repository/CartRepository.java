package cart.repository;

import cart.entity.CartEntity;

import java.util.List;

public interface CartRepository {

    CartEntity save(CartEntity cartEntity);

    List<CartEntity> findByUserId(Long userId);

    void deleteById(Long id);
}
