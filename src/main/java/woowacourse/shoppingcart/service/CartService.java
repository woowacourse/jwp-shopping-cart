package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.exception.shoppingcart.InvalidProductException;
import woowacourse.exception.shoppingcart.NotInCustomerCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.dto.CartItem;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartAddRequest;
import woowacourse.shoppingcart.dto.CartUpdateRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private static final int DEFAULT_QUANTITY = 1;

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByLoginCustomer(LoginCustomer loginCustomer) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customer.getId());

        final List<Cart> carts = new ArrayList<>();
        for (Long cartId : cartIds) {
            final CartItem cartItem = cartItemDao.findCartByCartId(cartId);
            carts.add(new Cart(cartId, cartItem.getProduct(), cartItem.getQuantity()));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Cart addCart(final LoginCustomer loginCustomer, final CartAddRequest request) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        Long customerId = customer.getId();
        try {
            Long cartId = cartItemDao.addCartItem(customerId, request.getProductId(), DEFAULT_QUANTITY);
            Long productId = cartItemDao.findProductIdById(cartId);
            return new Cart(cartId, productDao.findProductById(productId), cartItemDao.findQuantityById(cartId));
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final LoginCustomer loginCustomer, final Long cartId) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        validateCustomerCart(cartId, customer.getName());
        cartItemDao.deleteCartItem(cartId);
    }

    public void deleteAllCart(LoginCustomer loginCustomer) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        cartItemDao.deleteAllCart(customer.getId());
    }

    public Cart updateQuantity(LoginCustomer loginCustomer, CartUpdateRequest request, Long cartId) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        Long productId = cartItemDao.findProductIdById(cartId);
        cartItemDao.updateQuantity(customer.getId(), productId, request.getQuantity());
        Product product = productDao.findProductById(productId);
        return new Cart(cartId, product, cartItemDao.findQuantityById(cartId));
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
