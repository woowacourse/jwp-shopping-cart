package cart.dao;

import cart.entity.CartProductEntity;

import java.util.List;

public interface CarProductDao {
    void save(final CartProductEntity cart);

    void deleteById(final CartProductEntity cart);

    List<CartProductEntity> findByMemberId(final Long memberId);
}
