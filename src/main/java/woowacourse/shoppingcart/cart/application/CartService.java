package woowacourse.shoppingcart.cart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.support.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

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

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
