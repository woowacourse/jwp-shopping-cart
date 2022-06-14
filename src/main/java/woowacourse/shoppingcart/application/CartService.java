package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.DuplicateCartItemByProduct;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public CartResponse findCartByCustomerId(final Long customerId) {
        final Cart cart = cartItemDao.findCartByCustomerId(customerId);
        return CartResponse.of(cart);
    }

    public CartItemResponse addCart(final Long productId, final Long customerId) {
        if (cartItemDao.existByProduct(customerId, productId)) {
            throw new DuplicateCartItemByProduct();
        }

        final Product product = productDao.findProductById(productId);
        return CartItemResponse.of(cartItemDao.addCartItem(customerId, product));
    }

    public void deleteCartItem(final Long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        Cart cart = cartItemDao.findCartByCustomerId(customerId);
        CartItem cartItem = cartItemDao.findByCartId(cartId);
        if (!cart.isContains(cartItem)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public CartItemResponse updateCardItemQuantity(Long customerId, Long cartId, int quantity) {
        CartItem cartItem = cartItemDao.findByCartId(cartId);
        cartItemDao.updateCartItemQuantity(customerId, cartId, quantity);
        cartItem.updateQuantity(quantity);
        return CartItemResponse.of(cartItem);
    }

    public void deleteCart(Long customerId) {
        cartItemDao.deleteCart(customerId);
    }
}
