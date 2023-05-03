package cart.dao;

import cart.entity.CartEntity;

import java.util.List;

public interface CartDao {
    void save(final CartEntity cart);

    void deleteById(final Long memberId, final Long productId);

    List<CartEntity> findByMemberId(final Long memberId);
}
