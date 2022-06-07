package woowacourse.shoppingcart.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.repository.dao.CartItemDao;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;

    public CartItemRepository(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        return cartItemDao.findCartItemsByCustomerId(customerId);
    }
}
