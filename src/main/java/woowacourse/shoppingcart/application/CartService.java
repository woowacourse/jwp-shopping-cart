package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

import java.util.ArrayList;
import java.util.List;

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
            final Product product = productDao.findProductById(productId)
                    .orElseThrow(InvalidProductException::new);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    public List<Cart> findCartsByCustomer(final Customer customer) {
        final List<Long> cartIds = findCartIdsByCustomerId(customer.getId());

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId)
                    .orElseThrow(InvalidProductException::new);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final Customer customer) {
        productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        validateDuplicateCartItem(productId);
        try {
            return cartItemDao.addCartItem(customer.getId(), productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateDuplicateCartItem(Long productId) {
        if (cartItemDao.existByProductId(productId)) {
            throw new DuplicateCartItemException();
        }
    }

    public Long addCart(final Long productId, final String customerName) {
        productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
        final Long customerId = customerDao.findIdByUserName(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Customer customer, final Long productId) {
        productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);

        cartItemDao.deleteByProductIdAndCustomerId(customer.getId(), productId);
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
