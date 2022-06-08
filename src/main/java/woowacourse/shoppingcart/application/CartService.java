package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.LoginCustomer;
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

    public CartItemResponse add(final LoginCustomer loginCustomer, final CartItemCreateRequest cartItemCreateRequest) {
        final Product product = productDao.findProductById(cartItemCreateRequest.getProductId());
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        final CartItem cartItem = cartItemCreateRequest.toCartItem(product);

        Long cartItemId = cartItemDao.add(customer.getId(), cartItem);
        return CartItemResponse.of(cartItemId, cartItem);
    }

    public List<CartItem> findCartsByCustomerName(final LoginCustomer loginCustomer) {
        final List<Long> cartIds = findCartIdsByCustomerName(loginCustomer);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            cartItems.add(new CartItem(cartId, product));
        }
        return cartItems;
    }

    private List<Long> findCartIdsByCustomerName(final LoginCustomer loginCustomer) {
        final Long customerId = customerDao.findByLoginId(loginCustomer.getLoginId()).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public void deleteAll(LoginCustomer loginCustomer) {
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        cartItemDao.deleteAllByCustomerId(customer.getId());
    }

    public void deleteCart(final LoginCustomer loginCustomer, final Long cartId) {
        validateCustomerCart(cartId, loginCustomer);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final LoginCustomer loginCustomer) {
        final List<Long> cartIds = findCartIdsByCustomerName(loginCustomer);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
