package woowacourse.shoppingcart.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;

    public CartItemRepository(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public Long save(CartItem cartItem) {
        return cartItemDao.addCartItem(
                new CartItemEntity(cartItem.getCustomer().getId(), cartItem.getProduct().getId()));
    }

    public List<Long> findIdsByCustomerId(Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public void deleteCartItem(Long cartId) {
        cartItemDao.deleteCartItem(cartId);
    }
}
