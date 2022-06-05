package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.exception.domain.InvalidProductException;
import woowacourse.shoppingcart.exception.domain.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    public CartResponse findCartsByCustomerName(final String customerName) {
        final Cart cart = new Cart(findCartItemsByCustomerName(customerName));
        return CartResponse.from(cart);
    }

    private List<CartItem> findCartItemsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        return cartItemDao.findCartItemsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, Integer quantity, final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<CartItem> cartItems = findCartItemsByCustomerName(customerName);
        if (cartItems.stream()
            .noneMatch(item -> item.getId().equals(cartId))) {
            throw new NotInCustomerCartItemException();
        }
    }
}
