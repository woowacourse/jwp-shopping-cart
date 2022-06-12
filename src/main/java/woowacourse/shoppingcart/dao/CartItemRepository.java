package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Cart;

public interface CartItemRepository {
    Cart findById(Long id);

    List<Long> findProductIdsByCustomerId(final Long customerId);

    List<Cart> findAllByCustomerId(final Long customerId);

    Long findProductIdById(final Long cartId);

    Cart findByCustomerIdAndProductId(Long customerId, Long productId);

    Long addCartItem(final Long customerId, final Long productId, final long quantity, final boolean checked);

    void update(Long id, Cart cart);

    void deleteCartItem(final Long id);

    void deleteAllByCustomerId(Long customerId);

    void increaseQuantityById(Long id, long quantity);
}
