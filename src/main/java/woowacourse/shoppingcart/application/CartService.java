package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.exception.InvalidProductException;
import woowacourse.shoppingcart.application.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.dto.CartResponse;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CartResponse> findCartsByCustomerName(final String customerName) {
        Carts carts = findCartIdsByCustomerName(customerName);

        return carts.getElements().stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
    }

    private Carts findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return new Carts(cartItemDao.findAllByCustomerId(customerId));
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
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
        Carts carts = findCartIdsByCustomerName(customerName);

        if (carts.haveCartId(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateQuantity(final String userName, final Long cartId, final int quantity) {
        validateCustomerCart(cartId, userName);
        cartItemDao.updateQuantity(cartId, quantity);
    }
}
