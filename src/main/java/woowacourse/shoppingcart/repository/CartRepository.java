package woowacourse.shoppingcart.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class CartRepository {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartRepository(CartItemDao cartItemDao, CustomerDao customerDao,
                          ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomerName(final Email email) {
        final List<Long> cartIds = findCartIdsByCustomerEmail(email);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int quantity = cartItemDao.findQuantityById(productId);
            carts.add(new Cart(cartId, product, quantity));
        }

        return carts;
    }

    public List<Long> findCartIdsByCustomerEmail(final Email email) {
        final Long customerId = customerDao.findIdByUserEmail(email);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final int quantity, final Email email) {
        final Customer customer = customerDao.findByUserEmail(email);
        final Long customerId = customer.getId();
        try {
            final boolean hasProduct = hasProduct(email, productId);
            if (hasProduct) {
                throw new IllegalArgumentException("카트에 상품이 이미 존재합니다.");
            }
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void updateQuantity(final Email email, final Long productId, final int quantity) {
        final Long customerId = customerDao.findIdByUserEmail(email);
        cartItemDao.updateQuantity(customerId, productId, quantity);
    }

    public boolean hasProduct(final Email email, final Long productId) {
        try {
            final Long customerId = customerDao.findIdByUserEmail(email);
            cartItemDao.findIdByProductIdAndCustomerId(customerId, productId);
            return true;
        } catch (InvalidCartItemException e) {
            return false;
        }
    }

    public void deleteCart(final Long cartId) {
        cartItemDao.deleteCartItem(cartId);
    }
}
