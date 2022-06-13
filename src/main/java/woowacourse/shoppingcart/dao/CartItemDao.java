package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;

public interface CartItemDao {
    Optional<CartItemEntity> findById(Long cartItemId);

    List<CartItemEntity> findAllByCustomerId(Long customerId);

    Long save(final Long customerId, final CartItem cartItem);

    void update(Long cartItemId, CartItem newCartItem);

    void delete(final Long id);

    boolean isProductExisting(Long customerId, Long productId);
}
