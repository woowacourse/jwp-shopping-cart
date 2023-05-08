package cart.dao;

import cart.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    Optional<CartEntity> save(CartEntity cart, Long productId, Long memberId);

    Optional<CartEntity> findById(Long id);

    List<CartEntity> findByMemberId(Long memberId);

    List<CartEntity> findAll();

    CartEntity update(CartEntity entity);

    boolean existByIdAndMemberId(Long id, Long memberId);

    boolean existByMemberIdAndProductId(Long memberId, Long productId);

    void deleteById(Long id);
}
