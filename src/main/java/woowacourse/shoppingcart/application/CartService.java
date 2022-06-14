package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.NotFoundCustomerCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public Long addCart(final Long productId, final Long customerId) {
        try {
            return cartItemDao.save(customerId, productId);
        } catch (Exception e) {
            throw new NotFoundProductException();
        }
    }

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        return cartItemDao.findCartItemsByCustomerId(customerId);
    }

    public void updateCartItemQuantity(final Long customerId, final Long productId, final int quantity) {
        validateCustomerCart(customerId, productId);
        cartItemDao.updateQuantity(productId, quantity);
    }

    private void validateCustomerCart(final Long customerId, final Long productId) {
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
        if (!productIds.contains(productId)) {
            throw new NotFoundCustomerCartItemException();
        }
    }

    public void delete(final Long customerId, final List<Long> cartItemIds) {
        validateCustomerCarts(customerId, cartItemIds);
        cartItemDao.deleteCartItems(cartItemIds);
    }

    private void validateCustomerCarts(final Long customerId, final List<Long> cartItemIds) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (!cartIds.containsAll(cartItemIds)) {
            throw new NotFoundCustomerCartItemException();
        }
    }
}
