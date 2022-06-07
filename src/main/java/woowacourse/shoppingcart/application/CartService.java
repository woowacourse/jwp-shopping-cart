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
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomerId(long customerId) {
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int count = cartItemDao.findCountById(cartId);
            carts.add(new Cart(cartId, count, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerId(final long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final long customerId, final int count) {
        try {
            return cartItemDao.addCartItem(customerId, productId, count);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
