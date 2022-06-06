package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.entity.CartItemEntity;

public interface CartItemDao {
    Long addCartItem(final int customerId, final Long productId, int quantity);

    List<CartItemEntity> findCartByCustomerId(int customerId);

    boolean hasCartItem(Long cartId, int customerId);

    boolean hasProduct(int customerId, Long productId);

    boolean hasCart(Long cartItem, int customerId, Long productId);

    void deleteCartItem(Long cartId);

    void updateCartItem(Long cartItemId, int customerId, Long productId, int quantity);
}
