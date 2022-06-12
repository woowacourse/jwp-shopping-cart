package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.exception.domain.CartItemNotFoundException;
import woowacourse.shoppingcart.exception.domain.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.domain.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public CartResponse findCartsByCustomerId(final Long customerId) {
        final Cart cart = cartItemDao.findCartByCustomerId(customerId);
        return CartResponse.from(cart);
    }

    @Transactional
    public Long addCart(final Long productId, Integer quantity, final Long customerId) {
        try {
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void deleteCart(final Long customerId, final Long cartId) {
        validateCustomerHasCartItem(cartId, customerId);
        if (!cartItemDao.deleteById(cartId)) {
            throw new CartItemNotFoundException();
        }
    }

    private void validateCustomerHasCartItem(final Long cartId, final Long customerId) {
        final Cart cart = cartItemDao.findCartByCustomerId(customerId);
        final CartItem cartItem = cartItemDao.findCartItemById(cartId).orElseThrow(CartItemNotFoundException::new);
        if (!cart.contains(cartItem)) {
            throw new NotInCustomerCartItemException();
        }
    }

    @Transactional
    public void updateCartItemQuantity(Long customerId, Long cartId, Integer quantityValue) {
        if (!cartItemDao.updateQuantity(customerId, cartId, new Quantity(quantityValue))) {
            throw new CartItemNotFoundException();
        }
    }
}
