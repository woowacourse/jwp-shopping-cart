package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.entity.CartItemEntity;

public interface CartItemDao {
    Long addCartItem(final int customerId, final Long productId, int quantity);

    List<CartItemEntity> findCartByCustomerId(int customerId);
}
