package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customer.getId());
        return carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
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

    public void updateQuantity(final Long cartId, final int quantity, final Long customerId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.updateQuantity(quantity, cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customer.getId());
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
