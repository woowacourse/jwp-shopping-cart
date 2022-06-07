package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotFoundCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public Long addCart(final Long productId, final Long customerId) {
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        return cartItemDao.findProductsByCustomerId(customerId);
    }

    public void deleteCart(final Long customerId, final List<Long> cartItemIds) {
        validateCustomerCart(customerId, cartItemIds);
        cartItemDao.deleteCartItems(cartItemIds);
    }

    private void validateCustomerCart(final Long customerId, final List<Long> cartItemIds) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (!cartIds.containsAll(cartItemIds)) {
            throw new NotFoundCustomerCartItemException();
        }
    }
}
