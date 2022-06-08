package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.notfound.InvalidProductException;
import woowacourse.shoppingcart.exception.notfound.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private static final int INITIAL_QUANTITY = 1;

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long addCart(final Long customerId, final Long productId) {
        try {
            return cartItemDao.addCartItem(customerId, productId, INITIAL_QUANTITY);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public List<Cart> findCartsByCustomerName(final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    public void deleteCart(final Long customerId, final List<Long> cartIds) {
        validateCustomerCart(customerId, cartIds);
        cartItemDao.deleteCartItems(cartIds);
    }

    private void validateCustomerCart(final Long customerId, final List<Long> cartIds) {
        final List<Long> findByCustomerId = cartItemDao.findIdsByCustomerId(customerId);
        if (containSameElements(cartIds, findByCustomerId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private boolean containSameElements(final List<Long> from, final List<Long> to) {
        return from.containsAll(to) && to.containsAll(from);
    }

    public void update(final Long customerId, final Long productId, final Integer quantity) {
        final Cart cart = cartItemDao.findByCustomerIdAndProductId(customerId, productId);
        final Cart updatedCart = cart.applyQuantity(quantity);
        cartItemDao.update(updatedCart);
    }
}
