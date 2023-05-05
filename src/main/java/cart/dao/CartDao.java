package cart.dao;

import cart.entity.CartEntity;

import java.util.List;

public interface CartDao {
    void save(final CartEntity cart);

    void deleteById(final CartEntity cart);

    List<CartEntity> findByMemberId(final Long memberId);
}
