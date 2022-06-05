package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.InvalidProductException;
import woowacourse.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.dao.CartItemDao;
import woowacourse.shoppingcart.infra.dao.ProductDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerService customerService;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerService customerService,
                       final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerService = customerService;
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
        final Long customerId = customerService.getByName(customerName).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long customerId = customerService.getByName(customerName).getId();
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
