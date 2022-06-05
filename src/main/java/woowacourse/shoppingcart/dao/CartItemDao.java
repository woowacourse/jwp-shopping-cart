package woowacourse.shoppingcart.dao;

import java.util.List;

public interface CartItemDao {
    Long addCartItem(final int customerId, final Long productId, int quantity);

    List<Long> findCartIdsByCustomerId(int customerId);
}
