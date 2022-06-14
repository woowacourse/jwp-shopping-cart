package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.DuplicatedCartProductException;
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

    public List<CartResponse> findCartsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final List<Cart> carts  = cartItemDao.findCartsByCustomerId(customerId);

        return CartResponse.toCartResponses(carts);
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public CartResponse addCart(final Long productId, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        if (cartItemDao.existByProductId(productId, customerId)) {
            throw new DuplicatedCartProductException();
        }
        Long cartId = cartItemDao.addCartItem(customerId, productId);
        return CartResponse.of(cartItemDao.findCartById(cartId));
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

    public void deleteAllCart(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        cartItemDao.deleteAllCartItem(customerId);
    }

    public CartResponse updateCart(final Long cartId, final int quantity) {
        cartItemDao.updateQuantity(cartId, quantity);
        return CartResponse.of(cartItemDao.findCartById(cartId));
    }
}
