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
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
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

    @Transactional(readOnly = true)
    public List<Cart> findCartsByCustomerId(final Long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        return cartItemDao.findIdsByCustomerId(customer.getId());
    }

    public Long addCart(final Long productId, final Long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        try {
            return cartItemDao.addCartItem(customer.getId(), productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
