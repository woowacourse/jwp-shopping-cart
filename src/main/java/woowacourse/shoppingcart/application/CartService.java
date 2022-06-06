package woowacourse.shoppingcart.application;

import java.util.List;

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
    public CartResponse findCartsByCustomerId(final Long customerId) {
        final Cart cart = new Cart(cartItemDao.findCartItemsByCustomerId(customerId));
        return CartResponse.from(cart);
    }

    public Long addCart(final Long productId, Integer quantity, final Long customerId) {
        try {
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final Long cartId) {
        validateCustomerHasCartItem(cartId, customerId);
        cartItemDao.deleteById(cartId);
    }

    private void validateCustomerHasCartItem(final Long cartId, final Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        if (cartItems.stream()
            .noneMatch(item -> item.getId().equals(cartId))) {
            throw new NotInCustomerCartItemException();
        }
    }
}
