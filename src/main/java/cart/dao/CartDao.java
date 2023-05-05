package cart.dao;

import cart.domain.Cart;
import cart.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    Optional<CartEntity> save(Cart cart, long productId, long memberId);

    Optional<CartEntity> findById(Long id);

    List<CartEntity> findByMemberId(long memberId);
    
    List<CartEntity> findAll();

    CartEntity update(CartEntity entity);

    void deleteById(Long id);
}
